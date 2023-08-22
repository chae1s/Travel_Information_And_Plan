package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.*;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.entity.enums.Role;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.global.jwt.JwtTokenUtils;
import com.example.Final_Project_9team.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CustomUserDetailsManager manager;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserSignupDto dto) {

        log.info("회원가입: 비밀번호 입력 확인");
        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        log.info("회원가입: email 중복 확인");
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_EXISTED_EMAIL);
        }
        log.info("회원가입: nickname 중복 확인");
        if (userRepository.existsByNickname(dto.getNickname())) {
            throw new CustomException(ErrorCode.ALREADY_EXISTED_USERNAME);
        }

        userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .role(Role.ROLE_USER)
                .isDeleted(false)
                .build());
    }

    public JwtDto login(LoginDto dto, HttpServletResponse response) {
        UserDetails userDetails = manager.loadUserByUsername(dto.getEmail());
        log.info("\"{}\" 로그인", dto.getEmail());

        if (!passwordEncoder.matches(dto.getPassword(), userDetails.getPassword())) {
            log.info("비밀번호 불일치");
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        log.info("비밀번호 확인완료");
        String jwtToken = jwtTokenUtils.generateToken(userDetails);

        // 응답 헤더에 jwt 전달
        response.setHeader("Authorization", "Bearer " + jwtToken);
        log.info("jwt: {}", jwtTokenUtils.generateToken(userDetails));
        log.info("Header: {}", response.getHeader("Authorization"));
        return JwtDto.builder().token(jwtToken).build();
    }

    public UserResponseDto readUser(String username) {
        log.info("유저 \"{}\" 정보 조회", username);
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserResponseDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role(user.getRole().name())
                .build();
    }

}
