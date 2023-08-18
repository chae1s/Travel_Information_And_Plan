package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.ScheduleRequestDto;
import com.example.Final_Project_9team.dto.ScheduleResponseDto;
import com.example.Final_Project_9team.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ScheduleResponseDto create(@RequestBody ScheduleRequestDto requestDto, Authentication authentication) {

        return scheduleService.createSchedule(requestDto, authentication);
    }

}
