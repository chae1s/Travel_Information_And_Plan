package com.example.Final_Project_9team.chat.service;

import com.example.Final_Project_9team.chat.entity.ChatRoom;
import com.example.Final_Project_9team.chat.repository.ChatMessageRepository;
import com.example.Final_Project_9team.chat.repository.ChatRoomRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;


}
