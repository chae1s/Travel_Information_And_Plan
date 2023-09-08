package com.example.Final_Project_9team.dto;


import com.example.Final_Project_9team.entity.ScheduleItem;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


@Data
public class ScheduleItemResponseDto {
    private Long id;
    private Integer turn;
    private LocalDate tourDate;
    private Long itemId;
    private List<ItemListResponseDto> itemListResponses;
    private List<ItemPathDto> itemPaths;

    public static com.example.Final_Project_9team.dto.ScheduleItemResponseDto fromEntity(ScheduleItem scheduleItem) {
        com.example.Final_Project_9team.dto.ScheduleItemResponseDto dto = new com.example.Final_Project_9team.dto.ScheduleItemResponseDto();
        dto.setId(scheduleItem.getId());
        dto.setTurn(scheduleItem.getTurn());
        dto.setTourDate(scheduleItem.getTourDate());
        dto.setItemId(scheduleItem.getItem().getId());

        return dto;
    }

    public static ScheduleItemResponseDto fromEntity(LocalDate tourDate, List<ItemListResponseDto> itemListResponses, List<ItemPathDto> itemPaths) {
        ScheduleItemResponseDto dto = new ScheduleItemResponseDto();
        dto.setTourDate(tourDate);
        dto.setItemListResponses(itemListResponses);
        dto.setItemPaths(itemPaths);

        return dto;
    }

}
