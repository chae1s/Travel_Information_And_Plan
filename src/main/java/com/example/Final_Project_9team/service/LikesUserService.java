package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.entity.LikesUser;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.LikesUserRepository;
import com.example.Final_Project_9team.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikesUserService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final LikesUserRepository likesUserRepository;

    @Transactional
    public void likeUser(Long toUserId, String email) {
        // 존재하는 회원인지 검증
        User userFrom = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        User userTo = userRepository.findById(toUserId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        // 즐겨찾기된 상태인지 확인
        Optional<LikesUser> optionalLikesUser = likesUserRepository.findByUserFromAndUserTo(userFrom, userTo);
        if (optionalLikesUser.isPresent()) {
            // 이미 즐겨찾기된 상태에서 요청이 들어오는 경우 메시지 반환
            throw new CustomException(ErrorCode.ALREADY_LIKED_USER);
        }
        log.info("{} : {} 회원 즐겨찾기로 추가", userFrom.getNickname(), userTo.getNickname());
        likesUserRepository.save(LikesUser.builder()
                .userFrom(userFrom)
                .userTo(userTo)
                .isLike(true)
                .build());
    }
}

