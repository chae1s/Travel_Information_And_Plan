package com.example.Final_Project_9team.stomp.jpa;

import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomName;

    @OneToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @OneToMany
//            (mappedBy = "chat_room", fetch = FetchType.LAZY)
    private List<User> user = new ArrayList<>();
}
