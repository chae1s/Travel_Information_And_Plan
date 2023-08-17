package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.ScheduleRequestDto;
import com.example.Final_Project_9team.dto.ScheduleResponseDto;
import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.service.ScheduleService;
import com.example.Final_Project_9team.utils.GsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;;

@ExtendWith(MockitoExtension.class)
class ScheduleControllerTest {
    @InjectMocks
    private ScheduleController scheduleController;
    @Mock
    private ScheduleService scheduleService;
    private MockMvc mockMvc;

    private final String title = "즐거운 여행";
    private final LocalDateTime startDate = LocalDateTime.of(2023, 8, 20, 0, 0, 0);
    private final LocalDateTime endDate = LocalDateTime.of(2023, 8, 25, 0, 0, 0);

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(scheduleController)
                .build();
    }

    @Test
    @DisplayName("mockMvc Null Check")
    public void mockMvcIsNotNull() {
        assertThat(scheduleController).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    @DisplayName("일정 등록")
    public void createSchedule() throws Exception {
        // given
        String url = "/schedules";
        ScheduleRequestDto requestDto = new ScheduleRequestDto();
        doReturn(new ScheduleResponseDto(schedule())).when(scheduleService).createSchedule(any(ScheduleRequestDto.class));

        // when
        System.out.println(new GsonUtils().toJson(schedule()));
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new GsonUtils().toJson(requestDto))
        );
        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
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