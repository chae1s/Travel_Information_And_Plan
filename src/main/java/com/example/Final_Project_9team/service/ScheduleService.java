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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MatesRepository matesRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ScheduleItemRepository scheduleItemRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {
        // 로그인한 유저 정보 가져오기
//        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        User user = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .password("password")
                .nickname("nickname")
                .role(Role.ROLE_USER)
                .build();
        user = userRepository.save(user);

        Schedule schedule = dto.toEntity(user);
        // 일정 등록
        schedule = scheduleRepository.save(schedule);
        log.info("User Email : {}", user.getEmail());
        // 일정의 작성자 등록
        Mates mates = createScheduleWriter(user, schedule);

        return new ScheduleResponseDto(schedule);
    }

    public ScheduleResponseDto readSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
        List<Mates> mates = matesRepository.findAllBySchedule(schedule);
        List<MatesResponseDto> matesResponses = new ArrayList<>();
        for (Mates mate : mates) {
            if (mate.getIsAccepted()) {
                matesResponses.add(new MatesResponseDto(mate));
            }
        }

        return new ScheduleResponseDto(schedule, matesResponses);

    }

    public List<ScheduleItemResponseDto> createScheduleItem(Long scheduleId, List<ScheduleItemRequestDto> scheduleItemRequests) {
        // TODO: scheduleId로 schedule 조회
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
        // TODO: scheduleItemRequests 하나씩 repository에 저장
        List<ScheduleItemResponseDto> scheduleItemResponses = new ArrayList<>();
        for (ScheduleItemRequestDto scheduleItemRequest : scheduleItemRequests) {
            createScheduleItemEach(schedule, scheduleItemResponses, scheduleItemRequest);
        }

        return scheduleItemResponses;
    }

    private Mates createScheduleWriter(User user, Schedule schedule) {
        Mates mates = Mates.builder()
                .isHost(true)
                .isAccepted(true)
                .isDeleted(false)
                .user(user)
                .schedule(schedule)
                .build();

        return matesRepository.save(mates);
    }

    private void createScheduleItemEach(Schedule schedule, List<ScheduleItemResponseDto> scheduleItemResponses, ScheduleItemRequestDto scheduleItemRequest) {
        int turn = 1;
        for (Long itemId : scheduleItemRequest.getItemIds()) {
            Item item = itemRepository.findById(itemId).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
            ScheduleItem scheduleItem = scheduleItemRequest.toEntity(turn, schedule, item);
            scheduleItem = scheduleItemRepository.save(scheduleItem);
            scheduleItemResponses.add(new ScheduleItemResponseDto(scheduleItem));

            turn++;
        }
    }



}
