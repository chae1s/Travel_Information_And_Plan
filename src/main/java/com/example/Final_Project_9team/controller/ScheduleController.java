package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.*;
import com.example.Final_Project_9team.exception.SuccessCode;
import com.example.Final_Project_9team.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 일정 만들기
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ScheduleRequestDto requestDto, Authentication auth) {
        log.info("schedule Title : {}, schedule StartDate : {}, schedule Sido : {}", requestDto.getTitle(), requestDto.getStartDate(), requestDto.getSido());
//        scheduleService.createSchedule(requestDto, "testUser@gmail.com");
//        return ResponseEntity.ok(ResponseDto.getMessage(SuccessCode.CREATED.getMessage()));
//        return ResponseEntity.ok(scheduleService.createSchedule(requestDto, "testUser@gmail.com").getId()); //auth.getName()).getId()
        return ResponseEntity.ok(scheduleService.createSchedule(requestDto, auth.getName()).getId()); //auth.getName()).getId()
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
    @GetMapping("/write/{scheduleId:\\d+}")
    public ResponseEntity<ScheduleResponseDto> read(@PathVariable("scheduleId") Long scheduleId, Authentication auth) {

        return ResponseEntity.ok(scheduleService.readSchedule(scheduleId, auth.getName()));
    }

    // 세부 계획 저장하기
    @PostMapping("/{scheduleId}/schedule-items")
    public ResponseEntity<ResponseDto> createScheduleItems(@PathVariable("scheduleId") Long scheduleId) {

        scheduleService.createOrUpdateScheduleItems(scheduleId, false);

        return ResponseEntity.ok(ResponseDto.getMessage(SuccessCode.CREATED.getMessage()));
    }

    @PostMapping("/{scheduleId}/schedule-items/route")
    public ResponseEntity<List<ItemPathDto>> createScheduleItems(@PathVariable("scheduleId") Long scheduleId, @RequestBody ScheduleItemRequestDto scheduleItemRequest) {

        List<ItemPathDto> itemPaths = scheduleService.createRouteInformation(scheduleId, scheduleItemRequest);

        return ResponseEntity.ok(itemPaths);
    }

    @GetMapping("/{scheduleId:\\d+}")
    public ResponseEntity<ScheduleResponseDto> readScheduleBoard(@PathVariable Long scheduleId) {

        return ResponseEntity.ok(scheduleService.readScheduleByDisplay(scheduleId));
    }

    @PutMapping("/{scheduleId:\\d+}")
    public ResponseEntity<Long> updateSchedule(@PathVariable("scheduleId") Long scheduleId, @RequestBody ScheduleRequestDto dto, Authentication auth) {

        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleId, dto, auth.getName()).getId());
    }

    @PutMapping("/{scheduleId}/schedule-items")
    public ResponseEntity<ResponseDto> updateScheduleItems(@PathVariable("scheduleId") Long scheduleId) {

        scheduleService.createOrUpdateScheduleItems(scheduleId, true);

        return ResponseEntity.ok(ResponseDto.getMessage(SuccessCode.CREATED.getMessage()));
    }

    @PutMapping("/{scheduleId:\\d+}/display")
    public ResponseEntity<ResponseDto> updateScheduleDisplay(@PathVariable("scheduleId") Long scheduleId, Authentication auth) {

        scheduleService.updateDisplay(scheduleId, auth.getName());

        return ResponseEntity.ok(ResponseDto.getMessage(SuccessCode.CREATED.getMessage()));
    }



}
