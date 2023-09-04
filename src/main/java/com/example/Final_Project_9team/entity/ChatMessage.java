package com.example.Final_Project_9team.entity;

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

    private String message;
    private String time;

//    private Long roomId;
//    private String sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn
    private User user;
}
