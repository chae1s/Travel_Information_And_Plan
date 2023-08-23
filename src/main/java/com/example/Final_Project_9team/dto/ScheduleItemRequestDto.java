package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.ScheduleItem;
import com.example.Final_Project_9team.entity.item.Item;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ScheduleItemRequestDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime tourDate;
    private List<Long> itemIds = new ArrayList<>();

    public ScheduleItem toEntity(int turn, Schedule schedule, Item item) {
        return ScheduleItem.builder()
                .turn(turn)
                .tourDate(tourDate)
                .schedule(schedule)
                .item(item)
                .build();
    }

}
