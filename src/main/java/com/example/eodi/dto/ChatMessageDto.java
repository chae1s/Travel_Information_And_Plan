package com.example.eodi.dto;

import com.example.eodi.entity.User;
import com.example.eodi.entity.ChatMessage;
import com.example.eodi.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ChatMessageDto {
    private Long roomId;
    private String sender;
    private String message;
    private String time;

    public static ChatMessageDto fromEntity(ChatMessage messageEntity) {
        return new ChatMessageDto(
                messageEntity.getChatRoom().getId(),
                messageEntity.getUser().getNickname(), //sender->유저 닉네임으로 변경
                messageEntity.getMessage(),
                messageEntity.getCreatedAt().toString()
        );
    }

    public ChatMessage toEntity(ChatRoom chatRoom, User user) {
        ChatMessage messageEntity = ChatMessage.builder()
                .chatRoom(chatRoom)
                .user(user)
                .message(message)
                .time(time)
                .build();

        return messageEntity;
    }
}
