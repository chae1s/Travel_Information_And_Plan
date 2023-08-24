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

    // 일정 만들기
    @PostMapping
    public ResponseEntity<ResponseDto> create(@RequestBody ScheduleRequestDto requestDto, Authentication auth) {

        scheduleService.createSchedule(requestDto, auth);
        return ResponseEntity.ok(ResponseDto.getMessage(SuccessCode.CREATED.getMessage()));
    }

    // display true인 schedule 페이지 단위 조회
    @GetMapping
    public ResponseEntity<PageDto<ScheduleListResponseDto>> readAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(scheduleService.readAll(page, size));
    }

    // 일정 정보 보기 - 세부 계획을 짤 수 있는 페이지에 보여주기
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> read(@PathVariable("scheduleId") Long scheduleId, Authentication auth) {

        return ResponseEntity.ok(scheduleService.readSchedule(scheduleId, auth));
    }

    // 세부 계획 저장하기
    @PostMapping("/{scheduleId}/schedule-items")
    public ResponseEntity<ResponseDto> createScheduleItems(@PathVariable("scheduleId") Long scheduleId, @RequestBody List<ScheduleItemRequestDto> scheduleItemRequests) {

        scheduleService.createScheduleItems(scheduleId, scheduleItemRequests);

        return ResponseEntity.ok(ResponseDto.getMessage(SuccessCode.CREATED.getMessage()));
    }

}
