package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.global.ResponseMessageDto;
import com.example.Final_Project_9team.dto.UserSignupDto;
import com.example.Final_Project_9team.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseMessageDto> register(@Valid @RequestBody UserSignupDto dto) {
        userService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessageDto("회원가입이 완료되었습니다."));
    }


}
