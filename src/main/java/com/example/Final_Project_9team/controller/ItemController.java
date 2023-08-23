package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.ScheduleItemRequestDto;
import com.example.Final_Project_9team.dto.ScheduleItemResponseDto;
import com.example.Final_Project_9team.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ScheduleService scheduleService;

    // 한 개의 여행지 일정에 추가하기
    @PostMapping("/{itemId}/schedules/{scheduleId}")
    public ScheduleItemResponseDto createDateToScheduleItem(@PathVariable("itemId")Long itemId, @PathVariable("scheduleId")Long scheduleId,
                                                            @RequestBody ScheduleItemRequestDto scheduleItemRequest) {

        return scheduleService.createDateToScheduleItem(itemId, scheduleId, scheduleItemRequest);
    }
}
