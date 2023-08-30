package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.Mates;
import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatesRepository extends JpaRepository<Mates, Long> {
    List<Mates> findAllBySchedule(Schedule schedule);
    Boolean existsByScheduleIdAndUserId(Long scheduleId, Long userId);

    Optional<Mates> findByScheduleIdAndUserId(Long scheduleId, Long userId);



    List<Mates> findAllByUser(User user);

}
