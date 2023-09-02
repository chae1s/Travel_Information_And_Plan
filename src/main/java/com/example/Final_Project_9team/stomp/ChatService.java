package com.example.Final_Project_9team.stomp;

import com.example.Final_Project_9team.entity.Mates;
import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.MatesRepository;
import com.example.Final_Project_9team.repository.ScheduleRepository;
import com.example.Final_Project_9team.repository.UserRepository;
import com.example.Final_Project_9team.stomp.dto.ChatMessageDto;
import com.example.Final_Project_9team.stomp.dto.ChatRoomDto;
import com.example.Final_Project_9team.stomp.jpa.ChatMessage;
import com.example.Final_Project_9team.stomp.jpa.ChatMessageRepository;
import com.example.Final_Project_9team.stomp.jpa.ChatRoom;
import com.example.Final_Project_9team.stomp.jpa.ChatRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final MatesRepository matesRepository;
    private final ScheduleRepository scheduleRepository;
    public ChatService(
            ChatRoomRepository chatRoomRepository,
            ChatMessageRepository chatMessageRepository,
            UserRepository userRepository,
            MatesRepository matesRepository,
            ScheduleRepository scheduleRepository
    ) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
//        ChatRoom room = ChatRoom.builder()
//                .roomName("general")
//                .build();
//        this.chatRoomRepository.save(room);
        this.userRepository = userRepository;
        this.matesRepository = matesRepository;
        this.scheduleRepository = scheduleRepository;
    }

    // (리스트조회) 내가 속한 메이트의 채팅방 리스트 조회하기
    public List<ChatRoomDto> getChatRooms() { //String userEmail
        String userEmail ="sampleUser2@gmail.com";
        User user = userRepository.findByEmail(userEmail).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));
        List<Mates> matesList = matesRepository.findAllByUserIdAndIsAcceptedTrue(user.getId());
        List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();

        for (Mates mates : matesList) {
            log.info("mates.getSchedule()="+mates.getSchedule().getId());
            ChatRoom chatRoom = chatRoomRepository.findBySchedule(mates.getSchedule()).orElseThrow(()->
                    new CustomException(ErrorCode.CHATROOM_NOT_FOUND));
            chatRoomDtoList.add(ChatRoomDto.fromEntity(chatRoom));
        }

        return chatRoomDtoList;
    }

    public ChatRoomDto createChatRoom(ChatRoomDto chatRoomDto) {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomName(chatRoomDto.getRoomName())
                .build();

        return ChatRoomDto.fromEntity(chatRoomRepository.save(chatRoom));
    }
    // 채팅방 클릭 시
    public ChatRoomDto findRoomById(Long id) {
        String userEmail ="sampleUser2@gmail.com";
        User user = userRepository.findByEmail(userEmail).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow(
                ()->new CustomException(ErrorCode.CHATROOM_NOT_FOUND));
        return ChatRoomDto.fromEntity(chatRoom);
    }

    public void saveChatMessage(ChatMessageDto chatMessageDto) {
        String userEmail ="sampleUser2@gmail.com";

        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getRoomId()).orElseThrow(
                ()->new CustomException(ErrorCode.CHATROOM_NOT_FOUND));
        chatMessageRepository.save(chatMessageDto.newEntity(chatRoom,user));
    }

    public List<ChatMessageDto> getLast5Messages(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND));
        List<ChatMessageDto> chatMessageDtos = new ArrayList<>();
        List<ChatMessage> chatMessageEntities = chatMessageRepository.findTop5ByChatRoomOrderByIdDesc(chatRoom);
        Collections.reverse(chatMessageEntities);
        for (ChatMessage messageEntity: chatMessageEntities) {
            chatMessageDtos.add(ChatMessageDto.fromEntity(messageEntity)); //여기서 에러
        }
        return chatMessageDtos;
    }
}
