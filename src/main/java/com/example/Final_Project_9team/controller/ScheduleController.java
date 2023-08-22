package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.*;
import com.example.Final_Project_9team.exception.SuccessCode;
import com.example.Final_Project_9team.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/{scheduleId}/schedule-items")
    public List<ScheduleItemResponseDto> createScheduleItems(@PathVariable("scheduleId") Long scheduleId, @RequestBody List<ScheduleItemRequestDto> scheduleItemRequests) {

        List<ScheduleItemResponseDto> scheduleItemResponses = scheduleService.createScheduleItems(scheduleId, scheduleItemRequests);

        return scheduleItemResponses;
    }

}
