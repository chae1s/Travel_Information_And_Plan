package com.example.Final_Project_9team.chat.dto;

import com.example.Final_Project_9team.chat.entity.ChatMessage;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class ChatMessageResponseDto {
    // 채팅메시지(ChatMessage)불러오기 등의 요청으로 사용
    // 아래 5개 정보들을 담아 응답하게 된다.
    // 날짜 관련 필드들은 Format을 지정하여 String타입으로 전달
    private Long id;
    private String sender;
    private String message;
    private String createdDate;
    private String updatedDate;

    public ChatMessageResponseDto(ChatMessage entity) {
        this.id = entity.getId();
        this.sender = entity.getSender();
        this.message = entity.getMessage();
        this.createdDate = entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.updatedDate = entity.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

}
