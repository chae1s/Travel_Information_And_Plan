package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.MatesResponseDto;
import com.example.Final_Project_9team.dto.ResponseDto;
import com.example.Final_Project_9team.entity.Mates;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.MatesRepository;
import com.example.Final_Project_9team.repository.ScheduleRepository;
import com.example.Final_Project_9team.repository.UserRepository;
import com.example.Final_Project_9team.dto.ChatMessageDto;
import com.example.Final_Project_9team.dto.ChatRoomDto;
import com.example.Final_Project_9team.entity.ChatMessage;
import com.example.Final_Project_9team.repository.ChatMessageRepository;
import com.example.Final_Project_9team.entity.ChatRoom;
import com.example.Final_Project_9team.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final MatesRepository matesRepository;
    private final ScheduleRepository scheduleRepository;

    // (리스트조회) 내가 속한 메이트의 채팅방 리스트 조회하기
    public List<ChatRoomDto> getChatRooms(String userEmail) {
        userEmail ="sampleUser2@gmail.com"; // 화면테스트 위해 임시 데이터 설정
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
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow(
                ()->new CustomException(ErrorCode.CHATROOM_NOT_FOUND));
        return ChatRoomDto.fromEntity(chatRoom);
    }

    // 메시지 보내기
//    public ChatMessageDto sendMessage(Long roomId, ChatMessageDto dto) {
//        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(
//                ()->new CustomException(ErrorCode.CHATROOM_NOT_FOUND));
//
////        User sender = authFacade.getUser();
//        String userEmail ="sampleUser2@gmail.com";
//        User sender = userRepository.findByEmail(userEmail).orElseThrow(
//                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
//        ChatMessageDto payload = ChatMessageDto.fromEntity(chatMessageRepository.save(dto.toEntity(chatRoom,sender)));
//
//        User receiver = chatRoom.getUser().equals(sender) ?
//                room.getItem().getUser() :
//                room.getUser();
//        messagingTemplate.convertAndSend(
//                String.format(String.format("/topic/%s", receiver.getId())),
//                payload
//        );
//        messagingTemplate.convertAndSend(
//                String.format(String.format("/topic/%s", sender.getId())),
//                payload
//        );
//
//        return payload;
//    }

    // TODO 사용자 정보 받아와 검사하는 로직으로 변경해야함
    public void saveChatMessage(ChatMessageDto chatMessageDto) {
        String userEmail ="sampleUser2@gmail.com";
        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getRoomId()).orElseThrow(
                ()->new CustomException(ErrorCode.CHATROOM_NOT_FOUND));
        chatMessageRepository.save(chatMessageDto.toEntity(chatRoom,user));
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

    // 같은 채팅방 메이트들 조회
    public List<MatesResponseDto> readChatMates(Long roomId) {
        // 채팅방 안에 있는 유저들의 정보를 보여주기 위한 mates list
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND));
        List<Mates> mates = matesRepository.findAllBySchedule(chatRoom.getSchedule());

        List<MatesResponseDto> matesResponses = new ArrayList<>();
        for (Mates mate : mates) {
            if (mate.getIsAccepted()) {
                matesResponses.add(MatesResponseDto.fromEntity(mate));
            }
        }
        return matesResponses;
    }
    // 채팅방명 변경
    public ResponseDto updateRoomName(Long roomId,String roomName,String userEmail){
        userEmail ="sampleUser1@gmail.com"; // 화면테스트 위해 임시 데이터 설정

        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND));

        // 채팅방의 호스트가 맞는지 검사
        if (!chatRoom.getSchedule().getUser().equals(user)) {
            log.error("not matched host and chatroom");
            throw new CustomException(ErrorCode.USER_NOT_MATCHED_HOST);
        }

        chatRoom.setRoomName(roomName);
        chatRoomRepository.save(chatRoom);

        return ResponseDto.getMessage("채팅방명이 변경되었습니다.");
    }
}
