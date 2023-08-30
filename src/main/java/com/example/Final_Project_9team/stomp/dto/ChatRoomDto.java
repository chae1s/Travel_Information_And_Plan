package com.example.Final_Project_9team.stomp.dto;

import com.example.Final_Project_9team.dto.ScheduleRequestDto;
import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.stomp.jpa.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
    private Long id;
    private String roomName;

    public static ChatRoomDto fromEntity(ChatRoom entity) {
        return new ChatRoomDto(
                entity.getId(),
                entity.getRoomName()
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
