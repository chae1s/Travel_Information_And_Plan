package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.ScheduleRequestDto;
import com.example.Final_Project_9team.dto.ScheduleResponseDto;
import com.example.Final_Project_9team.entity.Mates;
import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.entity.enums.Role;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.MatesRepository;
import com.example.Final_Project_9team.repository.ScheduleRepository;
import com.example.Final_Project_9team.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MatesRepository matesRepository;
    private final UserRepository userRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {
        Schedule schedule = dto.toEntity();
        // 일정 등록
        schedule = scheduleRepository.save(schedule);
        // 로그인한 유저 정보 가져오기
//        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        User user = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .password("password")
                .nickname("nickname")
                .role(Role.ROLE_USER)
                .build();

        log.info("User Email : {}", user.getEmail());
        // 일정의 작성자 등록
        Mates mates = createScheduleWriter(user, schedule);

        return new ScheduleResponseDto(schedule, mates);
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
}
