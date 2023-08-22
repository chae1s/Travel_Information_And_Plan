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

    @PostMapping("/{itemId}/schedules/{scheduleId}")
    public ScheduleItemResponseDto createDateToScheduleItem(@PathVariable("itemId")Long itemId, @PathVariable("scheduleId")Long scheduleId,
                                                            @RequestBody ScheduleItemRequestDto scheduleItemRequest) {

        return scheduleService.createDateToScheduleItem(itemId, scheduleId, scheduleItemRequest);
    }
}
