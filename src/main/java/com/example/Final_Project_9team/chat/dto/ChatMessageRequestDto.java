package com.example.Final_Project_9team.chat.dto;

import com.example.Final_Project_9team.chat.entity.ChatMessage;
import com.example.Final_Project_9team.chat.entity.ChatRoom;
import lombok.Data;

@Data
public class ChatMessageRequestDto {
    private String sender;
    private String message;
    private ChatRoom chatRoom;

    public ChatMessage toEntity() {
        return ChatMessage.builder()
                .sender(this.sender)
                .message(this.message)
                .chatRoom(this.chatRoom)
                .build();
    }
}
