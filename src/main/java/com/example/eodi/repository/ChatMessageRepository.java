package com.example.eodi.repository;


import com.example.eodi.entity.ChatMessage;
import com.example.eodi.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findTop5ByChatRoomOrderByIdDesc(ChatRoom chatRoom);
    List<ChatMessage> findAllByChatRoomIdOrderByIdAsc(Long roomId);
    Optional<ChatMessage> findTop1ByChatRoomOrderByIdDesc(ChatRoom chatRoom);
}
