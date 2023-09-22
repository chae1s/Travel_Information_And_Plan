package com.example.eodi.repository;

import com.example.eodi.entity.Schedule;
import com.example.eodi.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findBySchedule(Schedule schedule);
}
