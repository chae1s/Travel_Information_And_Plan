package com.example.Final_Project_9team.chat.repository;

import com.example.Final_Project_9team.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
}
