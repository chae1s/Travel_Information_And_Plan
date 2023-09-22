package com.example.eodi.repository;

import com.example.eodi.entity.Schedule;
import com.example.eodi.entity.ScheduleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleItemRepository extends JpaRepository<ScheduleItem, Long> {
    List<ScheduleItem> findAllBySchedule(Schedule schedule);

    Integer countScheduleItemByScheduleAndTourDate(Schedule schedule, LocalDate tourDate);

    List<ScheduleItem> findAllByScheduleAndTourDateOrderByTurnAsc(Schedule schedule, LocalDate tourDate);

    boolean existsByScheduleId(Long scheduleId);

    List<ScheduleItem> findAllByScheduleOrderByTurnAsc(Schedule schedule);
}
