package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.*;
import com.example.Final_Project_9team.dto.user.UserResponseDto;
import com.example.Final_Project_9team.entity.Mates;
import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.ScheduleItem;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.entity.enums.Role;
import com.example.Final_Project_9team.entity.item.Item;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MatesRepository matesRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ScheduleItemRepository scheduleItemRepository;

    @Value("${NAVER_MAP_CLIENT_ID}")
    private String clientId;
    @Value("${NAVER_MAP_CLIENT_SECRET}")
    private String clientKey;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto, String email) {
        // 로그인한 유저 정보 가져오기
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Schedule schedule = dto.toEntity(user);
        // 일정 등록
        schedule = scheduleRepository.save(schedule);
        log.info("User Email : {}", user.getEmail());
        // 일정의 작성자 등록
        Mates mates = createScheduleWriter(user, schedule);

        return ScheduleResponseDto.fromEntity(schedule);
    }

    public ScheduleResponseDto readSchedule(Long scheduleId, String email) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // mates에 로그인한 사용자의 정보가 없을 경우 Exception 처리
        if (!scheduleRepository.existsByUserEmailAndScheduleId(email, scheduleId)) {
            log.error("{} {} 일정 접근 불가", user.getEmail(), schedule.getTitle());
            throw new CustomException(ErrorCode.USER_NO_AUTH);
        }

        Integer period = Period.between(schedule.getStartDate(), schedule.getEndDate()).getDays() + 1;

        // 세부 계획 작성 페이지에 보일 메이트의 정보를 보여주기 위한 userList
        List<Mates> mates = matesRepository.findAllBySchedule(schedule);
        List<UserResponseDto> userResponses = new ArrayList<>();
        for (Mates mate : mates) {
            if (mate.getIsAccepted()) {
                log.info("{} 일정 mates의 닉네임 : {}", schedule.getTitle(), mate.getUser().getNickname());
                userResponses.add(UserResponseDto.fromEntity(mate.getUser()));
            }
        }

        return ScheduleResponseDto.fromEntity(schedule, userResponses, period);

    }

    // 여행 일정 기간동안의 계획 한 번에 저장
    public List<ScheduleItemResponseDto> createScheduleItems(Long scheduleId, List<ScheduleItemRequestDto> scheduleItemRequests) {

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        List<ScheduleItemResponseDto> scheduleItemResponses = new ArrayList<>();
        for (ScheduleItemRequestDto scheduleItemRequest : scheduleItemRequests) {
            createScheduleItemEach(schedule, scheduleItemResponses, scheduleItemRequest);
            log.info("{} 계획 등록 완료", scheduleItemRequest.getTourDate());
        }

        return scheduleItemResponses;
    }

    public List<ItemPathDto> createRouteInformation(Long scheduleId, ScheduleItemRequestDto scheduleItemRequest) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        String start = "";
        String goal = "";
        String waypoints = "";

        return createRoutePosition(scheduleItemRequest.getItemIds());
    }

    // 여행지 상세 페이지에서 일정의 특정 날짜에 여행지 추가
    public ScheduleItemResponseDto createDateToScheduleItem(Long itemId, Long scheduleId, ScheduleItemRequestDto scheduleItemRequest) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        log.info("일정에 추가될 여행지 : {}", item.getName());
        log.info("여행지가 추가될 일정의 날짜 : {}", scheduleItemRequest.getTourDate());
        int count = scheduleItemRepository.countScheduleItemByScheduleAndTourDate(schedule, scheduleItemRequest.getTourDate());
        ScheduleItem scheduleItem = scheduleItemRequest.toEntity(count + 1, schedule, item);

        scheduleItem = scheduleItemRepository.save(scheduleItem);

        return ScheduleItemResponseDto.fromEntity(scheduleItem);
    }

    // schedule 작성자를 mates 테이블에 추가
    private Mates createScheduleWriter(User user, Schedule schedule) {
        log.info("{} 일정 메이트의 호스트로 저장", user.getEmail());
        Mates mates = Mates.builder()
                .isHost(true)
                .isAccepted(true)
                .isDeleted(false)
                .user(user)
                .schedule(schedule)
                .build();

        return matesRepository.save(mates);
    }

    // schedule에 일자별 세부 계획 한 번에 등록하기
    private void createScheduleItemEach(Schedule schedule, List<ScheduleItemResponseDto> scheduleItemResponses, ScheduleItemRequestDto scheduleItemRequest) {
        int turn = 1;

        log.info("{} 날짜에 저장될 여행지의 개수 : {}", scheduleItemRequest.getTourDate(), scheduleItemRequest.getItemIds().size());

        for (Long itemId : scheduleItemRequest.getItemIds()) {
            Item item = itemRepository.findById(itemId).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
            ScheduleItem scheduleItem = scheduleItemRequest.toEntity(turn, schedule, item);
            scheduleItem = scheduleItemRepository.save(scheduleItem);
            scheduleItemResponses.add(ScheduleItemResponseDto.fromEntity(scheduleItem));

            turn++;
        }
    }

    private void updateDisplay(String email, Long scheduleId) {
        User user = userRepository.findByEmail(email).get();

        Schedule schedule = scheduleRepository.findByIdAndIsDeletedFalse(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        if (!user.equals(schedule.getUser())) {
            throw new CustomException(ErrorCode.USER_NO_AUTH);
        }

        schedule.updateDisplay();
        scheduleRepository.save(schedule);
    }

    private List<ItemPathDto> createRoutePosition(List<Long> itemIds) {
        StringBuilder sb = new StringBuilder();
        String start = "";
        String goal = "";

        for (int i = 0; i < itemIds.size(); i++) {
            Item item = itemRepository.findById(itemIds.get(i)).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
            if (i == 0) {
                start = String.format("%s,%s", item.getLocation().getLatitude(), item.getLocation().getLongitude());
                continue;
            } else if (i == itemIds.size() - 1) {
                goal = String.format("%s,%s", item.getLocation().getLatitude(), item.getLocation().getLongitude());
                continue;
            } else {
                if (i > 1) sb.append("|");

                sb.append(String.format("%s,%s", item.getLocation().getLatitude(), item.getLocation().getLongitude()));
            }
        }

        String waypoints = sb.toString();

        log.info("start : {}, goal : {}, waypoints : {}", start, goal, waypoints);

        return createItemPathInformation(itemIds, start, goal, waypoints);
    }

    private List<ItemPathDto> createItemPathInformation(List<Long> itemIds, String start, String goal, String waypoints) {
        String naverMapUrl = String.format("https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving?start=%s&goal=%s&waypoints=%s", start, goal, waypoints);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.set("X-NCP-APIGW-API-KEY", clientKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.exchange(naverMapUrl, HttpMethod.GET, entity, String.class).getBody();

        // 에러코드 수정
        if (result == null) throw new CustomException(ErrorCode.SCHEDULE_NOT_FOUND);

        List<ItemPathDto> itemPaths = new ArrayList<>();

        if (waypoints.equals("")) createRouteInfoWithoutWaypoints(result, itemPaths);
        else createRouteInfoWithWaypoints(result, itemPaths);

        log.info(itemPaths.toString());

        return itemPaths;
    }

    private void createRouteInfoWithWaypoints(String result, List<ItemPathDto> itemPaths) {
        JsonElement element = JsonParser.parseString(result);
        JsonObject jsonObject = element.getAsJsonObject();
        JsonArray traoptimal = jsonObject.get("route").getAsJsonObject().get("traoptimal").getAsJsonArray();
        JsonObject summary = traoptimal.get(0).getAsJsonObject().get("summary").getAsJsonObject();

        int totalDistance = summary.get("distance").getAsInt();
        int totalDuration = summary.get("duration").getAsInt();

        int lastDistance = totalDistance;
        int lastDuration = totalDuration;
        JsonArray waypointsInfo = summary.getAsJsonArray("waypoints");

        for (JsonElement waypoint : waypointsInfo) {
            JsonObject object = waypoint.getAsJsonObject();
            int distance = object.get("distance").getAsInt();
            int duration = object.get("duration").getAsInt();

            itemPaths.add(ItemPathDto.getItemPath(distance / 1000, duration / 60000));
            lastDistance -= distance;
            lastDuration -= duration;
        }

        itemPaths.add(ItemPathDto.getItemPath(lastDistance / 1000, lastDuration / 60000));
        itemPaths.add(ItemPathDto.getItemPath(totalDistance / 1000, totalDuration / 60000));
    }

    private void createRouteInfoWithoutWaypoints(String result, List<ItemPathDto> itemPaths) {
        JsonElement element = JsonParser.parseString(result);
        JsonObject jsonObject = element.getAsJsonObject();
        JsonArray traoptimal = jsonObject.get("route").getAsJsonObject().get("traoptimal").getAsJsonArray();
        JsonObject summary = traoptimal.get(0).getAsJsonObject().get("summary").getAsJsonObject();

        int distance = summary.get("distance").getAsInt() / 1000;
        int duration = summary.get("duration").getAsInt() / 60000;

        itemPaths.add(ItemPathDto.getItemPath(distance, duration));

        // 총 이동거리로 표시
        itemPaths.add(ItemPathDto.getItemPath(distance, duration));
    }

    // display true인 schedule 전체 조회
    public PageDto<ScheduleListResponseDto> readAll(int page, int size) {
        Page<Schedule> pagedSchedules = scheduleRepository.findAllByDisplayTrueAndIsDeletedFalseOrderByIdDesc(
                PageRequest.of(page - 1, size)
        );

        Page<ScheduleListResponseDto> pagedDto
                = pagedSchedules.map(schedule -> ScheduleListResponseDto.fromEntity(schedule));
        return PageDto.fromPage(pagedDto);

    }
}
