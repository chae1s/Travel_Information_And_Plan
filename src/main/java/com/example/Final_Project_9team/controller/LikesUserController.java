package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.ResponseDto;
import com.example.Final_Project_9team.service.LikesUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/me")
public class LikesUserController {
    private final LikesUserService likesUserService;

    // 회원 즐겨찾기
    // POST /users/me/liked-user/{toUserId}
    @PostMapping("/liked-user/{toUserId}")
    public ResponseEntity<ResponseDto> likeUser(@PathVariable("toUserId") Long userId, Authentication auth) {
        likesUserService.likeUser(userId, auth.getName());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto().getMessage("즐겨찾기에 추가되었습니다."));
    }
}
