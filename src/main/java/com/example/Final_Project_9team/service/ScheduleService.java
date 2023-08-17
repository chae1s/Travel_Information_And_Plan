package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.ScheduleRequestDto;
import com.example.Final_Project_9team.dto.ScheduleResponseDto;
import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {
        Schedule schedule = dto.toEntity();
        schedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule);
    }
}
