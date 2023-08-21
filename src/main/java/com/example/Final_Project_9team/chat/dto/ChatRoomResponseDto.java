package com.example.Final_Project_9team.chat.dto;

import com.example.Final_Project_9team.chat.entity.ChatRoom;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class ChatRoomResponseDto {
    // 채팅방 입장 등의 요청으로 사용하기 위한 DTO
    private Long id;
    private String roomName;
    private String createdDate;
    private String updatedDate;

    public ChatRoomResponseDto(ChatRoom entity) {
        this.id = entity.getId();
        this.roomName = entity.getRoomName();
        this.createdDate = entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.updatedDate = entity.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }
}
