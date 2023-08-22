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

    @GetMapping("/me/schedules/after-day")
    public List<ScheduleListResponseDto> readSchedulesAfterToday() {

        return scheduleService.readSchedulesAfterToday();
    }
}
