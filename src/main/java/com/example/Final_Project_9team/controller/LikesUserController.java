package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.ResponseDto;
import com.example.Final_Project_9team.dto.user.UserResponseDto;
import com.example.Final_Project_9team.service.LikesUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 회원 즐겨찾기 취소
    // PUT /users/me/liked-user/{toUserId}
    @PutMapping("/liked-user/{toUserId}")
    public ResponseEntity<ResponseDto> unlikeUser(@PathVariable("toUserId") Long userId, Authentication auth) {
        likesUserService.cancleLikeUser(userId, auth.getName());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto().getMessage("즐겨찾기가 취소되었습니다."));
    }

    // 내가 즐겨찾기한 회원 조회
    // GET /users/me/liked-user/to
    @GetMapping("/liked-user/to")
    public ResponseEntity<List<UserResponseDto>> readLikedUserByMe(
            Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(likesUserService.readUserLikedByMe(auth.getName()));
    }

    // 나를 즐겨찾기한 회원 조회
    // GET /users/me/liked-user/from
    @GetMapping("/liked-user/from")
    public ResponseEntity<List<UserResponseDto>> readLikedUserMe(
            Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(likesUserService.readUserWhoLikedMe(auth.getName()));
    }
}
