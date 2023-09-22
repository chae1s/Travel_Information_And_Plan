package com.example.eodi.dto;


import com.example.eodi.entity.ScheduleItem;
import lombok.Data;

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

    public static com.example.eodi.dto.ScheduleItemResponseDto fromEntity(ScheduleItem scheduleItem) {
        com.example.eodi.dto.ScheduleItemResponseDto dto = new com.example.eodi.dto.ScheduleItemResponseDto();
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
