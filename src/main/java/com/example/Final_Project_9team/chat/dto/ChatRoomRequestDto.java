package com.example.Final_Project_9team.chat.dto;

import com.example.Final_Project_9team.chat.entity.ChatRoom;
import lombok.Data;

@Data
//채팅방 생성/수정 등의 요청으로 사용하기 위한 DTO
//사용자가 채팅방 이름을 결정하여 요청하게 된다.
public class ChatRoomRequestDto {
    private String roomName;
    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .roomName(this.roomName)
                .build();
    }
}
