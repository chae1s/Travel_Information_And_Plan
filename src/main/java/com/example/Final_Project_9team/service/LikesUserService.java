package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.user.UserResponseDto;
import com.example.Final_Project_9team.entity.LikesUser;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.LikesUserRepository;
import com.example.Final_Project_9team.repository.UserRepository;
import com.example.Final_Project_9team.utils.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikesUserService {
    private final UserRepository userRepository;
    private final LikesUserRepository likesUserRepository;
    private final UserUtils userUtils;


    // 회원 즐겨찾기 추가
    @Transactional
    public void likeUser(Long toUserId, String email) {

        // 즐겨찾기를 이용할 회원(현재 로그인한 회원) 확인
        log.info("즐겨찾기: \"{}\" 로그인한 회원 확인", email);
        User userFrom = userUtils.getUser(email);
//        User userFrom = userRepository.findByEmail(email).orElseThrow(
//                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 현재 회원을 추가하려고 할 경우
        if (userFrom.getId() == toUserId) {
            log.info("즐겨찾기: 자신을 즐겨찾기할 수 없음");
            throw new CustomException(ErrorCode.ERROR_BAD_REQUEST);
        }

        // 즐겨찾기에 추가될 회원 확인
        log.info("즐겨찾기: 추가할 회원 찾기");
        User userTo = userUtils.getUser(toUserId);
//        User userTo = userRepository.findById(toUserId).orElseThrow(
//                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 즐겨찾기된 상태인지 확인
        Optional<LikesUser> optionalLikesUser = likesUserRepository.findByUserFromAndUserTo(userFrom, userTo);

        // 즐겨찾기된 상태가 아닌 경우(likesUser에 존재하지 않음) 즐겨찾기 추가
        if (optionalLikesUser.isEmpty()) {
            log.info("즐겨찾기: likesUser is empty");
            likesUserRepository.save(LikesUser.builder()
                    .userFrom(userFrom)
                    .userTo(userTo)
                    .isDeleted(false)
                    .build());
        }

        // likesUser에 존재함, isDeleted 확인
        else {
            log.info("즐겨찾기: likesUser is present");
            LikesUser likesUser = optionalLikesUser.get();
            if (likesUser.getIsDeleted() == false) {
                // 이미 즐겨찾기된 상태에서 요청이 들어오는 경우 메시지 반환
                throw new CustomException(ErrorCode.ALREADY_LIKED_USER);
            } else {
                // 회원이 존재하는데 isDeleted == true인 경우
                log.info("즐겨찾기: error = 삭제된 데이터");
            }
        }
        log.info("즐겨찾기: from {} to {}", userFrom.getNickname(), userTo.getNickname());
    }

    // 즐겨찾기 취소
    // 취소시 데이터 삭제
    @Transactional
    public void cancleLikeUser(Long toUserId, String email) {

        // 즐겨찾기를 이용할 회원(현재 로그인한 회원) 확인
        log.info("즐겨찾기: \"{}\" 로그인한 회원 확인", email);
        User userFrom = userUtils.getUser(email);
//        User userFrom = userRepository.findByEmail(email).orElseThrow(
//                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 즐겨찾기에 추가될 회원 확인
        log.info("즐겨찾기: 추가할 회원 찾기");
        User userTo = userUtils.getUser(toUserId);
//        User userTo = userRepository.findById(toUserId).orElseThrow(
//                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 즐겨찾기된 상태인지 확인
        Optional<LikesUser> optionalLikesUser = likesUserRepository.findByUserFromAndUserTo(userFrom, userTo);

        // likesUser에 존재하지 않음
        if (optionalLikesUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_LIKED);
        }
        // likesUser에 존재함, isDeleted 확인
        else {
            log.info("즐겨찾기: likesUser is present");
            LikesUser likesUser = optionalLikesUser.get();
            if (!likesUser.getIsDeleted()) {
                // 즐겨찾기된 상태에서 취소, 데이터 물리 삭제
                likesUserRepository.delete(likesUser);
            } else {
                // 논리 삭제 상태에서 요청이 들어오는 경우 메시지 반환
                // 탈퇴된 회원과의 관계는 논리삭제, 회원 확인시 1차 검증됨
                throw new CustomException(ErrorCode.ERROR_NOT_FOUND);
            }
        }
        log.info("즐겨찾기: from {} to {} 취소", userFrom.getNickname(), userTo.getNickname());
    }

    // 현재 회원이 즐겨찾기한 회원 목록 조회
    // 결과가 없을 경우 빈 결과로 페이지 반환
    public List<UserResponseDto> readUserLikedByMe(String email) {
        User user = userUtils.getUser(email); // 현재 사용자, fromUser

        log.info("즐겨찾기 조회: \"{}.{}\" 즐겨찾기 회원 목록 조회", user.getId(), user.getNickname());

        // LikesUser를 UserResponseDto로 반환
        // 현재 회원이 From으로 있는 필드의 To 회원 조회, isDeleted == false여야 함
        List<UserResponseDto> likedUserDtoTo = user.getFromUsers()
                .stream()
                .filter(likesUser -> likesUser.getIsDeleted() == false)
                .map(likesUser -> UserResponseDto.fromEntity(likesUser.getUserTo()))
                .collect(Collectors.toList());
        return likedUserDtoTo;
    }

    // 현재 회원을 즐겨찾기한 회원 목록 조회
    // 결과가 없을 경우 빈 결과로 페이지 반환
    // isLiked 표시는 어떻게?
    public List<UserResponseDto> readUserWhoLikedMe(String email) {
        User user = userUtils.getUser(email); // 현재 사용자, toUser

        log.info("즐겨찾기 조회: \"{}.{}\" 나를 즐겨찾기한 회원 목록 조회", user.getId(), user.getNickname());

        // LikesUser를 UserResponseDto로 반환
        // 현재 회원이 To로 있는 필드의 From 회원 조회, isDeleted == false여야 g람
        List<UserResponseDto> likedUserDtoFrom = user.getToUsers()
                .stream()
                .filter(likesUser -> likesUser.getIsDeleted() == false)
                .map(likesUser -> UserResponseDto.fromEntity(likesUser.getUserFrom()))
                .collect(Collectors.toList());
        return likedUserDtoFrom;
    }

    // 모든 즐겨찾기 삭제
    // 현재 회원과 관계된 데이터 논리삭제
    @Transactional
    public void deleteLikesUserAll(User user) {
        // 즐겨찾기를 이용할 회원(현재 로그인한 회원) 확인
        log.info("즐겨찾기: \"{}.{}\" 로그인한 회원 확인", user.getId(), user.getNickname());

        // 현재 회원이 추가한 즐겨찾기 취소
        // 현재 회원이 From으로 있는 필드 && isDeleted == false인 경우만 isDeleted == true
        List<LikesUser> likedUserTo = user.getFromUsers();
        if (likedUserTo != null) {
            log.info("추가한 즐겨찾기 삭제");
            for (LikesUser likesUser : likedUserTo) {
                if (likesUser.getIsDeleted() == false) {
                    likesUser.delete();
                    likesUserRepository.save(likesUser);
                }
            }
        } else {
            log.info("삭제할 즐겨찾기(회원이 추가함) 없음");
        }
        // 현재 회원이 추가된 즐겨찾기 취소
        // 현재 회원이 To로 있는 필드 && isDeleted == false인 경우만 isDeleted == true
        List<LikesUser> likedUserFrom = user.getToUsers();
        if (likedUserFrom != null) {
            log.info("추가된 즐겨찾기 삭제");
            for (LikesUser likesUser : likedUserFrom) {
                if (likesUser.getIsDeleted() == false) {
                    likesUser.delete();
                    likesUserRepository.save(likesUser);
                }
            }
        } else {
            log.info("삭제할 즐겨찾기(회원이 추가됨) 없음");
        }
        log.info("즐겨찾기 삭제 완료");
    }
}



