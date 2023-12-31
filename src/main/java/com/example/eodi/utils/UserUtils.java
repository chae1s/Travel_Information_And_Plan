package com.example.eodi.utils;

import com.example.eodi.entity.User;
import com.example.eodi.exception.CustomException;
import com.example.eodi.exception.ErrorCode;
import com.example.eodi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserRepository userRepository;

    public User getUser(String email) {
        log.info("getUserByEmail: 회원 확인");
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        log.info("회원 {} 확인", user.getEmail());
        checkIsDeleted(user);
        return user;
    }

    public User getUser(Long id) {
        log.info("getUserById: 회원 확인");
        User user = userRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        log.info("회원 {} 확인", user.getEmail());
        checkIsDeleted(user);
        return user;
    }

    public void checkIsDeleted(User user){
        if (user.getIsDeleted() == true){
            log.info("회원 확인: 탈퇴된 회원");
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }
}
