package com.example.Final_Project_9team.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

// WebSocket요청을 받았을 때 행동을 정의할 클래스
@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleChatHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> sessions = new ArrayList<>();

    @Override
// WebSocket이 연결될 때 실행
    public void afterConnectionEstablished(
            WebSocketSession session
    ) throws Exception {
        sessions.add(session);
        log.info("{} connected, total sessions: {}", session, sessions.size());
    }
    @Override
// WebSocket으로 메세지를 받으면 실행
    protected void handleTextMessage(
            WebSocketSession session, // 메시지 보낸 클라이언트
            TextMessage message // 전달한 데이터
    ) throws Exception {
        String payload = message.getPayload();
        log.info("received: {}", payload);
        // 전달받은 메시지를 sessions에 저장된 모든 WebSocketSession에 다시 전달
        for (WebSocketSession connected: sessions) {
            connected.sendMessage(message);
        }
    }
    @Override
// WebSocket 연결이 종료될 때 실행
    public void afterConnectionClosed(
            WebSocketSession session,
            CloseStatus status
    ) throws Exception {
        log.info(session.getId());
        log.info("connection with {} closed", session);
        sessions.remove(session);
    }
}
