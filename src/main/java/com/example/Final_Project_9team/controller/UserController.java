package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.JwtDto;
import com.example.Final_Project_9team.dto.LoginDto;
import com.example.Final_Project_9team.dto.ProfileRequestDto;
import com.example.Final_Project_9team.global.ResponseDto;
import com.example.Final_Project_9team.dto.UserSignupDto;
import com.example.Final_Project_9team.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody UserSignupDto dto) {
        userService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.getMessage("회원가입이 완료되었습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody LoginDto dto, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(dto, response));
    }

    // test
    @GetMapping("/auth")
    public ResponseEntity<ResponseDto> test(Authentication auth){
        String user = auth.getName(); // nickname 출력하는 거 확인됨! CustomUserDetails에 설정한 대로 작동했다.
        log.info(user + "로그인");
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.getMessage(user + " 로그인 확인"));
    }

}
