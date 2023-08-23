package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.ScheduleItemRequestDto;
import com.example.Final_Project_9team.dto.ScheduleRequestDto;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ScheduleControllerTest {
    @InjectMocks
    private ScheduleController scheduleController;
    @Mock
    private ScheduleService scheduleService;
    private MockMvc mockMvc;

    private final String title = "즐거운 여행";
    private final LocalDate startDate = LocalDate.of(2023, 8, 20);
    private final LocalDate endDate = LocalDate.of(2023, 8, 25);

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

        // when
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new GsonUtils().toJson(requestDto))
        );
        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("여행계획 등록하기")
    public void createScheduleItem() throws Exception {
        // given
        String url = "/schedules/1";

        List<ScheduleItemRequestDto> requestDtos = new ArrayList<>();

        // when
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new GsonUtils().toJson(requestDtos))
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