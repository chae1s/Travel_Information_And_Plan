package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.*;
import com.example.Final_Project_9team.dto.user.UserResponseDto;
import com.example.Final_Project_9team.entity.*;
import com.example.Final_Project_9team.entity.enums.Role;
import com.example.Final_Project_9team.entity.item.Item;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.example.Final_Project_9team.dto.ChatRoomDto;
import com.example.Final_Project_9team.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final RedisTemplate<String, Object> redisTemplate;
    private final ItemPathRepository itemPathRepository;


    @Value("${NAVER_MAP_CLIENT_ID}")
    private String clientId;
    @Value("${NAVER_MAP_CLIENT_SECRET}")
    private String clientKey;

    private final ChatRoomRepository chatRoomRepository;
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto, String email) {
        // 로그인한 유저 정보 가져오기
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Schedule schedule = dto.toEntity(user);
        ChatRoom chatRoom = ChatRoomDto.toEntity(schedule); /*추가*/
        // 일정 등록
        schedule = scheduleRepository.save(schedule);
        log.info("User Email : {}", user.getEmail());

        chatRoomRepository.save(chatRoom);       /*추가 schedule보다 뒤에 저장해야해서 여기 두었습니다. */
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
        List<UserResponseDto> userResponses = readUserWriteSchedule(mates, schedule);

        if (!scheduleItemRepository.existsByScheduleId(schedule.getId())) {
            log.info("기존에 등록된 여행지 없음.");
            return ScheduleResponseDto.fromEntity(schedule, userResponses, period);
        }

        log.info("기존에 등록된 여행지 존재");
        LocalDate targetDate = schedule.getStartDate();
        LocalDate endDate = schedule.getEndDate();

        List<ScheduleItemResponseDto> scheduleItemResponses = readScheduleItemsAndItemPathAllDay(schedule);

        return ScheduleResponseDto.fromEntity(schedule, userResponses, scheduleItemResponses);

    }

    // 여행 일정 기간동안의 계획 한 번에 저장
    public void createOrUpdateScheduleItems(Long scheduleId, boolean isUpdate) {

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
        LocalDate targetDate = schedule.getStartDate();
        LocalDate endDate = schedule.getEndDate();

        int totalDistance = 0;
        int totalDuration = 0;

        // 스케줄 수정이면 기존의 schedulePath, itemPath 모두 삭제
        if (isUpdate) {
            List<ScheduleItem> scheduleItems = scheduleItemRepository.findAllBySchedule(schedule);
            List<ItemPath> itemPaths = itemPathRepository.findAllBySchedule(schedule);
            log.info("수정 전 scheduleItems: {}개, itemPaths: {}개 삭제", scheduleItems.size(), itemPaths.size() );
            scheduleItemRepository.deleteAll(scheduleItems);
            itemPathRepository.deleteAll(itemPaths);
        }

        while (targetDate.isBefore(endDate) || targetDate.isEqual(endDate)) {
            String id = String.format("no%d%s", scheduleId, targetDate);
            SchedulePathDto schedulePath = (SchedulePathDto) redisTemplate.opsForValue().get(id);
            // 에러 정리하기
            if (schedulePath == null) throw new CustomException(ErrorCode.SCHEDULE_NOT_FOUND);
            log.info("redis에 저장된 {} 날짜의 여행지 개수 : {}", targetDate, schedulePath.getScheduleItems().size());

            Map<String, Integer> pathInfoMap = createItemPath(schedule, targetDate, schedulePath);
            totalDistance += pathInfoMap.get("distance");
            totalDuration += pathInfoMap.get("duration");

            targetDate = targetDate.plusDays(1L);
        }

        log.info("{}의 총 이동시간 : {}, 총 이동거리 : {}", schedule.getTitle(), totalDuration, totalDistance);

        schedule.updateDurationAndDistance(totalDuration, totalDistance);
        scheduleRepository.save(schedule);
    }


    public List<ItemPathDto> createRouteInformation(Long scheduleId, ScheduleItemRequestDto scheduleItemRequest) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        List<ItemPathDto> itemPaths = createRoutePosition(scheduleItemRequest.getTourDestination());
        LocalDate tourDate = scheduleItemRequest.getTourDate();

        String id = String.format("no%d%s", scheduleId, tourDate);
        redisTemplate.opsForValue().set(id, SchedulePathDto.builder()
                .scheduleItems(scheduleItemRequest.getTourDestination())
                .itemPaths(itemPaths)
                .build());


        return itemPaths;
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

        log.info("{} 날짜에 저장될 여행지의 개수 : {}", scheduleItemRequest.getTourDate(), scheduleItemRequest.getTourDestination().size());


        for (ItemListResponseDto items : scheduleItemRequest.getTourDestination()) {
            Item item = itemRepository.findById(items.getId()).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
            ScheduleItem scheduleItem = scheduleItemRequest.toEntity(turn, schedule, item);
            scheduleItem = scheduleItemRepository.save(scheduleItem);
            scheduleItemResponses.add(ScheduleItemResponseDto.fromEntity(scheduleItem));

            turn++;
        }
    }

    public void updateDisplay(Long scheduleId, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Schedule schedule = scheduleRepository.findByIdAndIsDeletedFalse(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        if (!user.equals(schedule.getUser())) {
            throw new CustomException(ErrorCode.USER_NO_AUTH);
        }

        schedule.updateDisplay();
        scheduleRepository.save(schedule);
    }

    private List<ItemPathDto> createRoutePosition(List<ItemListResponseDto> tourDestination) {
        StringBuilder sb = new StringBuilder();
        String start = "";
        String goal = "";
        log.info(tourDestination.toString());
        for (int i = 0; i < tourDestination.size(); i++) {
            Item item = itemRepository.findById(tourDestination.get(i).getId()).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
            if (i == 0) {
                start = String.format("%s,%s", item.getLocation().getLatitude(), item.getLocation().getLongitude());
            } else if (i == tourDestination.size() - 1) {
                goal = String.format("%s,%s", item.getLocation().getLatitude(), item.getLocation().getLongitude());
            } else {
                if (i > 1) sb.append("|");

                sb.append(String.format("%s,%s", item.getLocation().getLatitude(), item.getLocation().getLongitude()));
            }
        }

        String waypoints = sb.toString();

        log.info("start : {}, goal : {}, waypoints : {}", start, goal, waypoints);

        return createItemPathInformation(tourDestination, start, goal, waypoints);
    }

    private List<ItemPathDto> createItemPathInformation(List<ItemListResponseDto> tourDestination, String start, String goal, String waypoints) {
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
        Page<Schedule> pagedSchedules = scheduleRepository.findAllByDisplayAndIsDeletedOrderByIdDesc(
                true, false,
                PageRequest.of(page - 1, size)
        );

        Page<ScheduleListResponseDto> pagedDto
                = pagedSchedules.map(schedule -> ScheduleListResponseDto.fromEntity(schedule));
        return PageDto.fromPage(pagedDto);

    }

    private Map<String, Integer> createItemPath(Schedule schedule, LocalDate tourDate, SchedulePathDto schedulePath) {
        List<ItemListResponseDto> scheduleItems = schedulePath.getScheduleItems();
        List<ItemPathDto> itemPaths = schedulePath.getItemPaths();

        int itemTurn = 1;

        ScheduleItem arrivalScheduleItem = createScheduleItem(schedule, scheduleItems.get(0).getId(), tourDate, itemTurn++);

        for (int i = 0; i < itemPaths.size() - 1; i++) {
            ScheduleItem departureScheduleItem = createScheduleItem(schedule, scheduleItems.get(i + 1).getId(), tourDate, itemTurn++);
            log.info("출발 여행지 : {}, 도착 여행지 : {}", arrivalScheduleItem.getItem().getName(), departureScheduleItem.getItem().getName());
            // itemPath db에 저장
            ItemPath itemPath = ItemPath.builder()
                    .schedule(schedule)
                    .turn(i + 1)
                    .distance(itemPaths.get(i).getDistance())
                    .duration(itemPaths.get(i).getDuration())
                    .tourDate(tourDate)
                    .arrivalScheduleItem(arrivalScheduleItem)
                    .departureScheduleItem(departureScheduleItem)
                    .build();
            log.info(
                    "{} - {} 이동 시간 : {}, 이동 거리 : {}", arrivalScheduleItem.getItem().getName(), departureScheduleItem.getItem().getName(),
                    itemPaths.get(i).getDuration(), itemPaths.get(i).getDistance());
            itemPathRepository.save(itemPath);
            arrivalScheduleItem = departureScheduleItem;
        }

        Map<String, Integer> pathInfoMap = new HashMap<>();
        pathInfoMap.put("distance", itemPaths.get(itemPaths.size() - 1).getDistance());
        pathInfoMap.put("duration", itemPaths.get(itemPaths.size() - 1).getDuration());

        return pathInfoMap;
    }

    private ScheduleItem createScheduleItem(Schedule schedule, Long itemId, LocalDate tourDate, int turn) {
        // 여행지 정보 가져오기
        Item arrivalItem = itemRepository.findById(itemId).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        // tourDate 날짜에 등록할 db에 저장
        ScheduleItem scheduleItem = ScheduleItem.builder()
                .schedule(schedule)
                .turn(turn)
                .tourDate(tourDate)
                .item(arrivalItem)
                .build();

        return scheduleItemRepository.save(scheduleItem);
    }

    public ScheduleResponseDto readScheduleByDisplay(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        Integer period = Period.between(schedule.getStartDate(), schedule.getEndDate()).getDays() + 1;

        // 세부 계획 작성 페이지에 보일 메이트의 정보를 보여주기 위한 userList
        List<Mates> mates = matesRepository.findAllBySchedule(schedule);
        List<UserResponseDto> userResponses = readUserWriteSchedule(mates, schedule);

        LocalDate targetDate = schedule.getStartDate();
        LocalDate endDate = schedule.getEndDate();

        List<ScheduleItemResponseDto> scheduleItemResponses = new ArrayList<>();

        while (targetDate.isBefore(endDate) || targetDate.isEqual(endDate)) {
            scheduleItemResponses.add(readScheduleItemsAndItemPath(schedule, targetDate));

            targetDate = targetDate.plusDays(1L);
        }

        return ScheduleResponseDto.fromEntity(schedule, userResponses, scheduleItemResponses);

    }

    private List<UserResponseDto> readUserWriteSchedule(List<Mates> mates, Schedule schedule) {
        List<UserResponseDto> userResponses = new ArrayList<>();
        for (Mates mate : mates) {
            if (mate.getIsAccepted()) {
                log.info("{} 일정 mates의 닉네임 : {}", schedule.getTitle(), mate.getUser().getNickname());
                userResponses.add(UserResponseDto.fromEntity(mate.getUser()));
            }
        }

        return userResponses;
    }

    private ScheduleItemResponseDto readScheduleItemsAndItemPath(Schedule schedule, LocalDate tourDate) {
        List<ScheduleItem> scheduleItems = scheduleItemRepository.findAllByScheduleAndTourDateOrderByTurnAsc(schedule, tourDate);
        List<ItemPath> itemPaths = itemPathRepository.findAllByScheduleAndTourDateOrderByTurn(schedule, tourDate);

        List<ItemListResponseDto> itemListResponses = new ArrayList<>();
        for (ScheduleItem scheduleItem : scheduleItems) {
            itemListResponses.add(ItemListResponseDto.fromEntity(scheduleItem.getItem()));
        }

        List<ItemPathDto> itemPathDtos = itemPaths.stream().map(itemPath -> ItemPathDto.getItemPath(itemPath.getDistance(), itemPath.getDuration()))
                .collect(Collectors.toList());

        return ScheduleItemResponseDto.fromEntity(tourDate, itemListResponses, itemPathDtos);
    }

    public ScheduleResponseDto updateSchedule(Long scheduleId, ScheduleRequestDto dto, String email) {
        // 로그인한 유저 정보 가져오기
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        // 일정 등록
        schedule.updateSchedule(dto);

        schedule = scheduleRepository.save(schedule);
        log.info("일정 {} 수정 완료", schedule.getTitle());


        return ScheduleResponseDto.fromEntity(schedule);
    }

    // 일정을 수정할 때 사용하는 메소드
    private List<ScheduleItemResponseDto> readScheduleItemsAndItemPathAllDay(Schedule schedule) {
        List<ScheduleItem> scheduleItems = scheduleItemRepository.findAllByScheduleOrderByTurnAsc(schedule);


        LocalDate startDate = scheduleItems.get(0).getTourDate();       // 여행 첫 번째 날짜
        LocalDate endDate = scheduleItems.get(scheduleItems.size() - 1).getTourDate();      // 여행 마지막 날짜


        List<ScheduleItemResponseDto> scheduleItemResponses = new ArrayList<>();

        while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            scheduleItemResponses.add(readScheduleItemsAndItemPath(schedule, startDate));

            startDate = startDate.plusDays(1L);
        }

        return scheduleItemResponses;
    }
}
