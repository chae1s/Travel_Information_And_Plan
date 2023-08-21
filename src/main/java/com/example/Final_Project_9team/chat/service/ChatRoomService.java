package com.example.Final_Project_9team.chat.service;

import com.example.Final_Project_9team.chat.dto.ChatMessageResponseDto;
import com.example.Final_Project_9team.chat.dto.ChatRoomRequestDto;
import com.example.Final_Project_9team.chat.dto.ChatRoomResponseDto;
import com.example.Final_Project_9team.chat.entity.ChatRoom;
import com.example.Final_Project_9team.chat.repository.ChatMessageRepository;
import com.example.Final_Project_9team.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    // String username-> Authentication 으로 바뀔 수 있음

    // 채팅방 생성
    public ChatRoomResponseDto createChatRoom(ChatRoomRequestDto chatRoom, String username) {

        ChatRoom chatRoomEntity = ChatRoom.builder()
                .roomName(chatRoom.getRoomName())
                .build();

        return new ChatRoomResponseDto(chatRoomEntity);
    }
    // 채팅방 조회
//    public List<ChatRoomResponseDto> getChatRooms() {
//        List<ChatRoom> chatRoomList = new ArrayList<>();
//
//    }
}
