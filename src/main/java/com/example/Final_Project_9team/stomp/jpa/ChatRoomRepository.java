package com.example.Final_Project_9team.stomp.jpa;

import com.example.Final_Project_9team.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findBySchedule(Schedule schedule);
}
