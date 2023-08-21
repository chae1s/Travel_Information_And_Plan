package com.example.Final_Project_9team.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final SimpleChatHandler simpleChatHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 어떤 주소에 어떤 핸들러를 활용할지를 정의하는 메소드
        registry.addHandler(simpleChatHandler, "ws/chat").setAllowedOrigins("*");
    }
}
