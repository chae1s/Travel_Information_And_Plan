package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.*;
import com.example.Final_Project_9team.global.ResponseDto;
import com.example.Final_Project_9team.global.util.SecurityUtils;
import com.example.Final_Project_9team.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody UserSignupDto dto) {
        userService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.getMessage("회원가입이 완료되었습니다."));
    }

    // 로그인 및 jwt 발급
    // jwt가 응답 헤더와 body에 중복으로 전달됨 -> 차후 필요에 따라 선택
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody LoginDto dto, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(dto, response));
    }


    // 로그인한 회원 정보 조회
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> readUser(Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.readUser(auth.getName()));
    }

    // 회원정보 수정
    // nickname은 unique해야 하기 때문에 별도로 검증로직 필요, 현재
//    @PatchMapping("/me")
//    public ResponseEntity<JwtDto> updateUserNickName(@Valid @RequestBody UserUpdateDto dto, Authentication auth) {
//        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(dto, auth.getName()));
//    }


    // role test
    @GetMapping("/roleUSer")
//    @PreAuthorize("hasAnyRole('USER')")
    public String roleTest(Authentication auth) {
        log.info(auth.getName() + " 로그인, 권한 : {}" + auth.getAuthorities());
        return "test";
    }
}
