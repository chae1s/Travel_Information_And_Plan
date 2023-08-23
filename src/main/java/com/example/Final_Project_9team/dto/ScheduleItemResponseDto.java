package com.example.Final_Project_9team.dto;


import com.example.Final_Project_9team.entity.ScheduleItem;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;


@Data
public class ScheduleItemResponseDto {
    private Long id;
    private Integer turn;
    private LocalDate tourDate;
    private Long itemId;

    public static ScheduleItemResponseDto fromEntity(ScheduleItem scheduleItem) {
        ScheduleItemResponseDto dto = new ScheduleItemResponseDto();
        dto.setId(scheduleItem.getId());
        dto.setTurn(scheduleItem.getTurn());
        dto.setTourDate(scheduleItem.getTourDate());
        dto.setItemId(scheduleItem.getItem().getId());

        return dto;
    }

}
