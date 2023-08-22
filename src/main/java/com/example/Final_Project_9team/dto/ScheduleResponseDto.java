package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.Schedule;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer sumDistance;
    private Integer sumDuration;
    private Boolean display;
    private Integer period;
    private List<MatesResponseDto> matesResponses;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.description = schedule.getDescription();
        this.startDate = schedule.getStartDate();
        this.endDate = schedule.getEndDate();
        this.sumDistance = schedule.getSumDistance();
        this.sumDuration = schedule.getSumDuration();
        this.display = schedule.getDisplay();
    }

    public ScheduleResponseDto(Schedule schedule, List<MatesResponseDto> matesResponses) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.description = schedule.getDescription();
        this.startDate = schedule.getStartDate();
        this.endDate = schedule.getEndDate();
        this.sumDistance = schedule.getSumDistance();
        this.sumDuration = schedule.getSumDuration();
        this.display = schedule.getDisplay();
        this.period = Period.between(schedule.getStartDate(), schedule.getEndDate()).getDays() + 1;
        this.matesResponses = matesResponses;
    }
}
