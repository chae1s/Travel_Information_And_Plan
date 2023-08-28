package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.auth.JwtDto;
import com.example.Final_Project_9team.dto.user.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
            throw new CustomException(ErrorCode.ALREADY_EXISTED_NICKNAME);
        }

        userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .role(Role.ROLE_USER)
                .isDeleted(false)
                .build());
    }

    public JwtDto login(UserLoginDto dto, HttpServletResponse response) {
        UserDetails userDetails = manager.loadUserByUsername(dto.getEmail());
        log.info("\"{}\" 로그인", dto.getEmail());

        if (!passwordEncoder.matches(dto.getPassword(), userDetails.getPassword())) {
            log.info("login: 비밀번호 불일치");
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        log.info("login: 비밀번호 확인완료");
        String jwtToken = jwtTokenUtils.generateToken(userDetails);

        // 응답 헤더에 jwt 전달
        response.setHeader("Authorization", "Bearer " + jwtToken);
        log.info("Header: {}", response.getHeader("Authorization"));
        return JwtDto.builder().token(jwtToken).build();
    }

    public UserResponseDto readUser(String email) {
        log.info("유저 \"{}\" 정보 조회", email);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserResponseDto.fromEntity(user);
    }


    // 회원 검색
    // 검색어가 포함된 닉네임, 이메일을 가진 회원을 반환한다.
    public Page<UserResponseDto> findUser(String keyword, Integer pageNumber, Integer pageSize, String email) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        log.info("user 검색");
        List<User> findByEmail = userRepository.findAllByEmailContainingAndIsDeletedIsFalseAndUsernameNot(keyword, email);
        List<User> findByNickname = userRepository.findAllByNicknameContainingAndIsDeletedIsFalseAndUsernameNot(keyword, email);
        List<User> mergedList = new ArrayList<>();
        mergedList.addAll(findByEmail);
        mergedList.addAll(findByNickname);

        // 중복제거 후 닉네임 기준으로 정렬
        List<User> distinctAndSorted = mergedList.stream()
                .distinct()
                .sorted(Comparator.comparing(User::getNickname)) // 닉네임 기준으로 정렬
                .collect(Collectors.toList());

        Page<User> userPaged = new PageImpl<>(distinctAndSorted, pageable, distinctAndSorted.size());
        Page<UserResponseDto> userListResponseDto = userPaged.map(user -> UserResponseDto.fromEntity(user));
        return userListResponseDto;
    }

    @Transactional
    public UserResponseDto updateUser(UserUpdateDto dto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        log.info("회원정보 수정: 닉네임 중복 여부 확인");
        String newNickname = dto.getNickname();
        // 새로운 닉네임과 기존 닉네임이 다를 경우 중복확인 수행
        if (!newNickname.equals(user.getNickname())) {
            if (userRepository.existsByNickname(dto.getNickname())) {
                throw new CustomException(ErrorCode.ALREADY_EXISTED_NICKNAME);
            }
            // dto의 내용으로 user 반영
            user.updateUser(newNickname);
        }
        log.info(user.getEmail());
        userRepository.save(user);

        return UserResponseDto.fromEntity(user);

    }

    // 비밀번호 수정
    @Transactional
    public void updateUserPassword(UserUpdatePwDto dto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        log.info("비밀번호 수정: 비밀번호 입력 확인");
        if (!dto.getNewPassword().equals(dto.getPasswordCheck())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        user.updatePassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    // 회원 탈퇴
    // soft deleted 구현
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

//        }        if (user.getProfileImg() != null) {
        // 프로필 이미지 제거
//            fileHandler.fileDelete(user.getProfileImg());
//        }
        userRepository.delete(user);
    }

    // 비밀번호 검증
    public boolean verifyPassword(UserVerifyPwDto dto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        log.info(user.getPassword());
        log.info(passwordEncoder.encode(dto.getPassword()));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            log.info("login: 비밀번호 불일치");
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        return true;




    }

}
