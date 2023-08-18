package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.Schedule;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ScheduleRequestDto {
    private String title;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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
                .sumDuration(0)
                .display(false)
                .build();
    }
}
