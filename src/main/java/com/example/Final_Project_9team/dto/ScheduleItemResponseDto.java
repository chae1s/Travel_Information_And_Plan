package com.example.Final_Project_9team.dto;


import com.example.Final_Project_9team.entity.ScheduleItem;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class ScheduleItemResponseDto {
    private Long id;
    private Integer turn;
    private LocalDate tourDate;
    private Long itemId;

    public ScheduleItemResponseDto(ScheduleItem scheduleItem) {
        this.id = scheduleItem.getId();
        this.turn = scheduleItem.getTurn();
        this.tourDate = scheduleItem.getTourDate();
        this.itemId = scheduleItem.getItem().getId();
    }
}
