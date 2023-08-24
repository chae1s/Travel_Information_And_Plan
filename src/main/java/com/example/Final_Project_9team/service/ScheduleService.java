package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.*;
import com.example.Final_Project_9team.entity.Mates;
import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.ScheduleItem;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.entity.enums.Role;
import com.example.Final_Project_9team.entity.item.Item;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto, Authentication auth) {
        // 로그인한 유저 정보 가져오기
        User user = userRepository.findByEmail(auth.getName()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Schedule schedule = dto.toEntity(user);
        // 일정 등록
        schedule = scheduleRepository.save(schedule);
        log.info("User Email : {}", user.getEmail());
        // 일정의 작성자 등록
        Mates mates = createScheduleWriter(user, schedule);

        return ScheduleResponseDto.fromEntity(schedule);
    }

    public ScheduleResponseDto readSchedule(Long scheduleId, Authentication auth) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
        User user = userRepository.findByEmail(auth.getName()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // mates에 로그인한 사용자의 정보가 없을 경우 Exception 처리
        if (matesRepository.existsByScheduleAndUser(schedule, user)) {
            log.error("{} {} 일정 접근 불가", user.getEmail(), schedule.getTitle());
            throw new CustomException(ErrorCode.USER_NO_AUTH);
        }

        // 세부 계획 작성 페이지에 보일 메이트의 정보를 보여주기 위한 mates list
        List<Mates> mates = matesRepository.findAllBySchedule(schedule);

        List<MatesResponseDto> matesResponses = new ArrayList<>();
        for (Mates mate : mates) {
            if (mate.getIsAccepted()) {
                matesResponses.add(MatesResponseDto.fromEntity(mate));
            }
        }

        return ScheduleResponseDto.fromEntity(schedule, matesResponses);

    }

    // 여행지 상세 페이지에서 여행지를 일정에 추가할 때 보기 위한 일정 목록
    public List<ScheduleListResponseDto> readSchedulesAfterToday(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        LocalDate today = LocalDate.now();
        log.info("일정 조회 기준 - 오늘 날짜 : {}", today);

        List<Schedule> schedules = scheduleRepository.findByUserAndEndDateGreaterThanEqual(user, today);
        List<ScheduleListResponseDto> scheduleListResponses = schedules.stream().map(schedule -> ScheduleListResponseDto.fromEntity(schedule))
                .collect(Collectors.toList());

        return scheduleListResponses;
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
