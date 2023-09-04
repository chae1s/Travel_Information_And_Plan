package com.example.Final_Project_9team.controller.socket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("schedules/chat")
public class ChatController {

    @GetMapping
    public String index() {
        return "chat-lobby";
    }

    @GetMapping("{roomId}")
    public String enterRoom(){
        return "chat-room";
    }
}
