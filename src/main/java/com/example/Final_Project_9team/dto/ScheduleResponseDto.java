package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.Mates;
import com.example.Final_Project_9team.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer sumDistance;
    private Integer sumDuration;
    private Boolean display;
    private List<Mates> mates = new ArrayList<>();

    public ScheduleResponseDto(Schedule schedule, Mates mates) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.description = schedule.getDescription();
        this.startDate = schedule.getStartDate();
        this.endDate = schedule.getEndDate();
        this.sumDistance = schedule.getSumDistance();
        this.sumDuration = schedule.getSumDuration();
        this.display = schedule.getDisplay();
        this.mates.add(mates);
    }
}
