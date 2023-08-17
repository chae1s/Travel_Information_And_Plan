package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.ScheduleRequestDto;
import com.example.Final_Project_9team.dto.ScheduleResponseDto;
import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.repository.ScheduleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;
    @InjectMocks
    private ScheduleService scheduleService;

    private final String title = "즐거운 여행";
    private final LocalDateTime startDate = LocalDateTime.of(2023, 8, 20, 0, 0, 0);
    private final LocalDateTime endDate = LocalDateTime.of(2023, 8, 25, 0, 0, 0);

    @Test
    @DisplayName("일정 등록")
    public void createSchedule() {
        // given

        doReturn(schedule()).when(scheduleRepository).save(any(Schedule.class));

        // when
        ScheduleRequestDto requestDto = new ScheduleRequestDto();
        requestDto.setTitle(title);
        requestDto.setDescription("제주도");
        requestDto.setStartDate(startDate);
        requestDto.setEndDate(endDate);

        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto);

        // then
        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getStartDate()).isEqualTo(startDate);
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