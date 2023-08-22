package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByUserAndEndDateGreaterThanEqual(User user, LocalDateTime today);
}
