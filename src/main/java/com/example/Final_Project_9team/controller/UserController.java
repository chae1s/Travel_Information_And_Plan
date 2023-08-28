package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.ResponseDto;
import com.example.Final_Project_9team.dto.auth.JwtDto;
import com.example.Final_Project_9team.dto.user.*;
import com.example.Final_Project_9team.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.Final_Project_9team.service.ScheduleService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 회원가입
    // POST /users/register
    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody UserSignupDto dto) {
        userService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.getMessage("회원가입이 완료되었습니다."));
    }

    // 로그인 및 jwt 발급
    // jwt가 응답 헤더와 body에 중복으로 전달됨 -> 차후 필요에 따라 선택
    // POST /users/login
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody UserLoginDto dto, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(dto, response));
    }

    // 로그인한 회원 정보 조회
    // GET /users/me
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> readUser(Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.readUser(auth.getName()));
    }

    // 회원 검색
    // GET /users?q=keyword
    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> findUser(
            @RequestParam("q") String keyword,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            Authentication auth){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUser(keyword, page, limit, auth.getName()));

    }
    // 회원정보 수정
    // 비밀번호를 요구하지 않음
    // PUT /users/me
    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UserUpdateDto dto, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(dto, auth.getName()));
    }

    // 비밀번호 수정
    // 별도의 엔드포인트에서 비밀번호 인증 후 진입
    // PUT /users/me/pass-word
    @PutMapping("/me/pass-word")
    public ResponseEntity<ResponseDto> updateUserPassword(@Valid @RequestBody UserUpdatePwDto dto, Authentication auth){
        userService.updateUserPassword(dto, auth.getName());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto().getMessage("비밀번호가 수정되었습니다."));
    }

    // 비밀번호 인증
    // 현재 로그인한 유저의 비밀번호와 입력한 비밀번호가 맞는지 검증 후 boolean으로 반환
    // POST /users/me/verify-password
    @PostMapping("/me/verify-password")
    private ResponseEntity<Boolean> verifyPassword(@RequestBody UserVerifyPwDto dto, Authentication auth){
        return ResponseEntity.status(HttpStatus.OK).body(userService.verifyPassword(dto, auth.getName()));
    }

}
