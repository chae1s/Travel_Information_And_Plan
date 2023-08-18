package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.ScheduleRequestDto;
import com.example.Final_Project_9team.dto.ScheduleResponseDto;
import com.example.Final_Project_9team.entity.Mates;
import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.repository.MatesRepository;
import com.example.Final_Project_9team.repository.ScheduleRepository;
import com.example.Final_Project_9team.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private MatesRepository matesRepository;
    @InjectMocks
    private ScheduleService scheduleService;

    private final String title = "즐거운 여행";
    private final LocalDateTime startDate = LocalDateTime.of(2023, 8, 20, 0, 0, 0);
    private final LocalDateTime endDate = LocalDateTime.of(2023, 8, 25, 0, 0, 0);

    @Test
    @DisplayName("일정 등록 후 mates에 일정과 작성자 등록하기")
    public void createSchedule() {
        // given
        doReturn(Optional.of(user())).when(userRepository).findByEmail(any(String.class));
        // 여행 일정 등록
        doReturn(schedule()).when(scheduleRepository).save(any(Schedule.class));
        // 여행 일정 작성자 등록
        doReturn(mates()).when(matesRepository).save(any(Mates.class));
        // when
        ScheduleRequestDto requestDto = new ScheduleRequestDto();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user().getEmail(), "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto, authentication);

        // then
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getStartDate()).isEqualTo(startDate);
        assertThat(responseDto.getMates().get(0).getUser().getEmail()).isEqualTo("test@gmail.com");
    }

    private Mates mates() {
        return Mates.builder()
                .id(1L)
                .user(user())
                .schedule(schedule())
                .isHost(true)       // 일정을 만든 유저이므로 호스트는 true
                .isAccepted(true)   // 일정을 만든 유저이므로 초대 수락 여부는 true
                .isDeleted(false)
                .build();
    }

    private User user() {
        return User.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();
    }

    private Schedule schedule() {

        return Schedule.builder()
                .id(1L)
                .title(title)
                .description("제주도")
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

}