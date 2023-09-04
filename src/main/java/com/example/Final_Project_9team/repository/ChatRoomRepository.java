package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findBySchedule(Schedule schedule);
}
