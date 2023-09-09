package com.example.Final_Project_9team.repository;


import com.example.Final_Project_9team.entity.ChatMessage;
import com.example.Final_Project_9team.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findTop5ByChatRoomOrderByIdDesc(ChatRoom chatRoom);

    Optional<ChatMessage> findTop1ByChatRoomOrderByIdDesc(ChatRoom chatRoom);
}
