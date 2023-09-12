package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.ItemPath;
import com.example.Final_Project_9team.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ItemPathRepository extends JpaRepository<ItemPath, Long> {
    List<ItemPath> findAllByScheduleAndTourDateOrderByTurn(Schedule schedule, LocalDate tourDate);

    List<ItemPath> findAllBySchedule(Schedule schedule);
}
