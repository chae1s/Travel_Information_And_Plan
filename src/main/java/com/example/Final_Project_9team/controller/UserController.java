package com.example.Final_Project_9team.controller;


import com.example.Final_Project_9team.dto.ScheduleListResponseDto;
import com.example.Final_Project_9team.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final ScheduleService scheduleService;

    // 나의 일정 중 날짜 기준으로 목록 조회하기
    @GetMapping("/me/schedules/after-day")
    public List<ScheduleListResponseDto> readSchedulesAfterToday() {

        return scheduleService.readSchedulesAfterToday();
    }
}
