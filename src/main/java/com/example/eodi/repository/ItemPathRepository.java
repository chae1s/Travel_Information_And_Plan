package com.example.eodi.repository;

import com.example.eodi.entity.ItemPath;
import com.example.eodi.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ItemPathRepository extends JpaRepository<ItemPath, Long> {
    List<ItemPath> findAllByScheduleAndTourDateOrderByTurn(Schedule schedule, LocalDate tourDate);

    List<ItemPath> findAllBySchedule(Schedule schedule);
}
