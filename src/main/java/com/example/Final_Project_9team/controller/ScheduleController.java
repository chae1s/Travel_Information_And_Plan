package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.ResponseDto;
import com.example.Final_Project_9team.dto.ScheduleRequestDto;
import com.example.Final_Project_9team.dto.ScheduleResponseDto;
import com.example.Final_Project_9team.exception.SuccessCode;
import com.example.Final_Project_9team.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ResponseDto> create(@RequestBody ScheduleRequestDto requestDto) {

        scheduleService.createSchedule(requestDto);
        return ResponseEntity.ok(ResponseDto.getMessage(SuccessCode.CREATED.getMessage()));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> read(@PathVariable("scheduleId") Long scheduleId) {

        return ResponseEntity.ok(scheduleService.readSchedule(scheduleId));
    }

}
