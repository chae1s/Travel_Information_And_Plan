package com.example.Final_Project_9team.stomp;

import com.example.Final_Project_9team.stomp.dto.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
public class ChatRestController {
    private final ChatService chatService;

    @GetMapping("rooms")
    public ResponseEntity<List<ChatRoomDto>> getChatRooms(){ /*Authentication authentication*/
        return ResponseEntity.ok(chatService.getChatRooms()); //authentication.getName()
    }

//    @PostMapping("rooms")
//    public ResponseEntity<ChatRoomDto> createRoom(@RequestBody ChatRoomDto chatRoomDto){
//        return ResponseEntity.ok(chatService.createChatRoom(chatRoomDto));
//    }

    @GetMapping("rooms/{id}")
    public ResponseEntity<ChatRoomDto> getRoomName(@PathVariable("id") Long roomId) {
        return ResponseEntity.ok(chatService.findRoomById(roomId));
    }
}
