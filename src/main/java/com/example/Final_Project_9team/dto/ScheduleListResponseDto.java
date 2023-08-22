package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ScheduleListResponseDto {
    private Long id;
    private String title;
    private String description;
    private UserResponseDto userResponse;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<LocalDateTime> tourDates;

    public ScheduleListResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.description = getDescription();
        this.userResponse = new UserResponseDto(schedule.getUser());
        this.startDate = schedule.getStartDate();
        this.endDate = schedule.getEndDate();
        this.tourDates = new ArrayList<>();
        int period = Period.between(schedule.getStartDate().toLocalDate(), schedule.getEndDate().toLocalDate()).getDays() + 1;
        for (int i = 0; i < period; i++) {
            tourDates.add(schedule.getStartDate().plusDays(i));
        }
    }

}
