package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.UserSignupDto;
import com.example.Final_Project_9team.dto.CustomUserDetails;
import com.example.Final_Project_9team.entity.enums.Role;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final CustomUserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;


    public void registerUser(UserSignupDto dto) {
        log.info("회원가입: email 중복 확인");
        if (manager.userExists(dto.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_EXISTED_EMAIL);
        }

        log.info("회원가입: 비밀번호 입력 확인");
        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        manager.createUser(CustomUserDetails.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .role(Role.ROLE_USER)
                .build()
        );


    }
}
