package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.user.UserResponseDto;
import com.example.Final_Project_9team.entity.LikesUser;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.LikesUserRepository;
import com.example.Final_Project_9team.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikesUserService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final LikesUserRepository likesUserRepository;



    // 회원 즐겨찾기 추가
    @Transactional
    public void likeUser(Long toUserId, String email) {

        // 즐겨찾기를 이용할 회원(현재 로그인한 회원) 확인
        log.info("즐겨찾기: \"{}\" 로그인한 회원 확인", email);
        User userFrom = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 즐겨찾기에 추가될 회원 확인
        log.info("즐겨찾기: 추가할 회원 찾기");
        User userTo = userRepository.findById(toUserId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 즐겨찾기된 상태인지 확인
        Optional<LikesUser> optionalLikesUser = likesUserRepository.findByUserFromAndUserTo(userFrom, userTo);

        // likesUser에 존재하지 않음
        if (optionalLikesUser.isEmpty()) {
            // 한번도 즐겨찾기된 적 없는 경우 추가
            log.info("즐겨찾기: likesUser is empty");
            likesUserRepository.save(LikesUser.builder()
                    .userFrom(userFrom)
                    .userTo(userTo)
                    .isLike(true)
                    .build());
        }
        // likesUser에 존재함, isLiked 확인
        else {
            log.info("즐겨찾기: likesUser is present");
            LikesUser likesUser = optionalLikesUser.get();
            if (!likesUser.getIsLike()) {
                // 즐겨찾기 취소된 상태에서 다시 추가하려는 경우 추가
                likesUser.updateIsLike(true);
                likesUserRepository.save(likesUser);
            } else {
                // 이미 즐겨찾기된 상태에서 요청이 들어오는 경우 메시지 반환
                throw new CustomException(ErrorCode.ALREADY_LIKED_USER);
            }
        }
        log.info("즐겨찾기: from {} to {}", userFrom.getNickname(), userTo.getNickname());
    }

    // 즐겨찾기 취소
    @Transactional
    public void cancleLikeUser(Long toUserId, String email) {

        // 즐겨찾기를 이용할 회원(현재 로그인한 회원) 확인
        log.info("즐겨찾기: \"{}\" 로그인한 회원 확인", email);
        User userFrom = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 즐겨찾기에 추가될 회원 확인
        log.info("즐겨찾기: 추가할 회원 찾기");
        User userTo = userRepository.findById(toUserId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 즐겨찾기된 상태인지 확인
        Optional<LikesUser> optionalLikesUser = likesUserRepository.findByUserFromAndUserTo(userFrom, userTo);

        // likesUser에 존재하지 않음
        if (optionalLikesUser.isEmpty()) {
            // 한번도 즐겨찾기된 적 없는 경우 예외
            throw new CustomException(ErrorCode.USER_NOT_LIKED);
        }
        // likesUser에 존재함, isLiked 확인
        else {
            log.info("즐겨찾기: likesUser is present");
            LikesUser likesUser = optionalLikesUser.get();
            if (likesUser.getIsLike()) {
                // 즐겨찾기된 상태에서 취소
                likesUser.updateIsLike(false);
                likesUserRepository.save(likesUser);
            } else {
                // 이미 false인 상태에서 요청이 들어오는 경우 메시지 반환
                throw new CustomException(ErrorCode.ALREADY_UNLIKED_USER);
            }
        }
        log.info("즐겨찾기: from {} to {} 취소", userFrom.getNickname(), userTo.getNickname());
    }

    public Page<UserResponseDto> readUserLikedByMe(String email, Integer pageNumber, Integer pageSize) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        log.info("즐겨찾기 조회: \"{}.{}\" 즐겨찾기 회원 목록 조회", user.getId(), user.getNickname());

        // LikesUser를 UserResponseDto로 반환
        // 현재 회원이 즐겨찾기에 추가했는지 여부
        // 현재 회원이 From이면서 대상 회원이 To인 컬럼이 존재하고, 해당 컬럼의 isLike가 true인 경우 isLikeByMe 필드를 반환한다.
        List<UserResponseDto> likedUserDtoTo = user.getFromUsers()
                .stream()
                .filter(likesUser -> likesUser.getIsLike())
                .map(likesUser -> UserResponseDto.fromEntity(likesUser.getUserTo(), likesUser.getIsLike()))
                .collect(Collectors.toList());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<UserResponseDto> likedUserToPagedDto =  new PageImpl<>(likedUserDtoTo, pageable, likedUserDtoTo.size());
        return likedUserToPagedDto;
    }
}


