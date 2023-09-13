package com.example.Final_Project_9team.controller.socket;

import com.example.Final_Project_9team.dto.ChatMessageDto;
import com.example.Final_Project_9team.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

//@Controller
//@RequiredArgsConstructor
//public class SocketController {
//    private ChatService chatService;
//
//    @MessageMapping("/receive")
//    @SendTo("/send")
//    public ChatMessageDto chatMessagehandler(ChatMessageDto chatMessageDto) {
//        return  chatService.
//    }
//
//}
