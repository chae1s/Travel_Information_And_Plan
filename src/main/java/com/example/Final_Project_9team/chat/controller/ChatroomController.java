package com.example.Final_Project_9team.chat.controller;

import com.example.Final_Project_9team.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@Slf4j
public class ChatroomController {
    private final ChatRoomService chatRoomService;

//     내가 방을 만들어서 들어가는 경우
//    @PostMapping
//    public ResponseEntity<ResponseDto> createRoom(@RequestBody ChatroomRequestDto dto) {
//        return ResponseEntity.ok(chatroomService.createChatRoom(dto,ge))
//    }

}
