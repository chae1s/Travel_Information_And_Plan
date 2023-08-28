package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.dto.user.UserResponseDto;
import com.example.Final_Project_9team.entity.Schedule;
import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Data
public class ScheduleListResponseDto {
    private Long id;
    private String title;
    private String description;
    private String sido;
    private UserResponseDto userResponse;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<LocalDate> tourDates;

    public static ScheduleListResponseDto fromEntity(Schedule schedule) {
        ScheduleListResponseDto dto = new ScheduleListResponseDto();
        dto.setId(schedule.getId());
        dto.setTitle(schedule.getTitle());
        dto.setDescription(schedule.getDescription());
        dto.setSido(schedule.getSido());
        dto.setUserResponse(UserResponseDto.fromEntity(schedule.getUser()));
        dto.setStartDate(schedule.getStartDate());
        dto.setEndDate(schedule.getEndDate());
        List<LocalDate> tourDates = new ArrayList<>();
        int period = Period.between(schedule.getStartDate(), schedule.getEndDate()).getDays() + 1;
        for (int i = 0; i < period; i++) {
            tourDates.add(schedule.getStartDate().plusDays(i));
        }
        dto.setTourDates(tourDates);


        return dto;
    }



}
