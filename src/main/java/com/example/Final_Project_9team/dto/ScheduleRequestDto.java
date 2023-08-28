package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ScheduleRequestDto {
    private String title;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String sido;
    private Integer sumDistance;
    private Integer sumDuration;
    private Boolean display;

    public Schedule toEntity(User user) {
        return Schedule.builder()
                .title(title)
                .description(description)
                .user(user)
                .startDate(startDate)
                .endDate(endDate)
                .sido(sido)
                .sumDistance(0)
                .sumDuration(0)
                .isDeleted(false)
                .display(false)
                .build();
    }
}
