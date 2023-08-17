package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.Schedule;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleRequestDto {
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer sumDistance;
    private Integer sumDuration;
    private Boolean display;

    public Schedule toEntity() {
        return Schedule.builder()
                .title(title)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .sumDistance(0)
                .sumDistance(0)
                .display(false)
                .build();
    }
}
