package com.example.Final_Project_9team.chat.entity;

import com.example.Final_Project_9team.chat.entity.ChatRoom;
import com.example.Final_Project_9team.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

}
