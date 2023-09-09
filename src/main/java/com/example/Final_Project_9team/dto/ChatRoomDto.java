package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
    private Long id;
    private String roomName;
    private String latestMessage;

    public static ChatRoomDto fromEntity(ChatRoom entity) {
        return new ChatRoomDto(
                entity.getId(),
                entity.getRoomName(),
                null
        );
    }

    public static ChatRoomDto fromEntity(ChatRoom entity, String latestMessage) {
        return new ChatRoomDto(
                entity.getId(),
                entity.getRoomName(),
                latestMessage
        );
    }

    public static ChatRoom toEntity(Schedule schedule) {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomName(schedule.getTitle())
                .schedule(schedule)
                .build();
        return chatRoom;
    }
}
