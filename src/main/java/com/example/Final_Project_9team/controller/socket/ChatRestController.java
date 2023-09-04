package com.example.Final_Project_9team.controller.socket;

import com.example.Final_Project_9team.dto.MatesResponseDto;
import com.example.Final_Project_9team.dto.ResponseDto;
import com.example.Final_Project_9team.dto.ChatRoomDto;
import com.example.Final_Project_9team.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("schedules/chat")
@RequiredArgsConstructor
public class ChatRestController {
    private final ChatService chatService;

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomDto>> getChatRooms( /*Authentication authentication*/
    ){
        return ResponseEntity.ok(chatService.getChatRooms("authentication.getName()")); //authentication.getName()
    }

//    @PostMapping("rooms")
//    public ResponseEntity<ChatRoomDto> createRoom(@RequestBody ChatRoomDto chatRoomDto){
//        return ResponseEntity.ok(chatService.createChatRoom(chatRoomDto));
//    }
    // 채팅방 단일 조회
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<ChatRoomDto> getRoomName(@PathVariable("roomId") Long roomId) {
        return ResponseEntity.ok(chatService.findRoomById(roomId));
    }

    // 해당 채팅방 유저들 조회
    @GetMapping("/rooms/{roomId}/chat-users")
    public ResponseEntity<List<MatesResponseDto>> readChatMates(@PathVariable("roomId") Long roomId) {
        return ResponseEntity.ok(chatService.readChatMates(roomId));
    }
    // 채팅방명 변경
    @PostMapping("/rooms/{roomId}")
    public ResponseEntity<ResponseDto> updateRoomName(@PathVariable("roomId") Long roomId,
                                                      @RequestBody ChatRoomDto chatRoomDto
//                                                      Authentication authentication
                                                      ) {
        return ResponseEntity.ok(chatService.updateRoomName(roomId, chatRoomDto.getRoomName(), "authentication.getName()"));
    }
}
