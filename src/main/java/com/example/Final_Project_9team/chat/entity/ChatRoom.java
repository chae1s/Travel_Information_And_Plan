package com.example.Final_Project_9team.chat.entity;

import com.example.Final_Project_9team.chat.dto.ChatRoomRequestDto;
import com.example.Final_Project_9team.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomName;

    @OneToMany(mappedBy = "chatRoom")
    // 해당 채팅방에서 기록된 모든 채팅
    private List<ChatMessage> chatMessageList = new ArrayList<>();
    // 채팅방 이름 변경
    public Long update(ChatRoomRequestDto requestDto) {
        this.roomName = requestDto.getRoomName();
        return this.id;
    }
}
