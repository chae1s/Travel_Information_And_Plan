package com.example.eodi.dto;

import com.example.eodi.entity.Schedule;
import com.example.eodi.entity.ScheduleItem;
import com.example.eodi.entity.item.Item;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ScheduleItemRequestDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tourDate;
    private List<ItemListResponseDto> tourDestination = new ArrayList<>();

    public ScheduleItem toEntity(int turn, Schedule schedule, Item item) {
        return ScheduleItem.builder()
                .turn(turn)
                .tourDate(tourDate)
                .schedule(schedule)
                .item(item)
                .build();
    }
}
