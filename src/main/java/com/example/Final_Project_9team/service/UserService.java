package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.auth.JwtDto;
import com.example.Final_Project_9team.dto.user.*;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.entity.enums.Role;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.global.jwt.JwtTokenUtils;
import com.example.Final_Project_9team.repository.UserRepository;
import com.example.Final_Project_9team.utils.UserUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
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
    private final ProfileService profileService;
    private final CustomUserDetailsManager manager;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final LikesUserService likesUserService;
    private final MatesService matesService;
    private final UserUtils userUtils;

    // 회원등록
    @Transactional
    public void registerUser(UserSignupDto dto) {
//        log.info("회원가입: 비밀번호 입력 확인");
//        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
//            throw new CustomException(ErrorCode.INVALID_PASSWORD);
//        }
        log.info("회원가입: email 중복 확인 {}", dto.getEmail());
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_EXISTED_EMAIL);
        }
        log.info("회원가입: nickname 중복 확인 {}", dto.getNickname());
        if (userRepository.existsByNickname(dto.getNickname())) {
            throw new CustomException(ErrorCode.ALREADY_EXISTED_NICKNAME);
        }

        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .role(Role.ROLE_USER)
                .isDeleted(false)
                .build();
        userRepository.save(user);
        profileService.createProfile(user);
        log.info("회원가입 완료 pk:{}, email:{}, nickname:{}", user.getId(), user.getEmail(), user.getNickname());

    }

    // 로그인
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

    // 회원정보 조회
    public UserResponseDto readUser(String email) {
        log.info("유저 \"{}\" 정보 조회", email);
        User user = userUtils.getUser(email);
        return UserResponseDto.fromEntity(user);
    }

    // 회원 검색
    // keyword가 포함된 email, nickname으로 회원 검색, 탈퇴회원 및 본인을 제외하고 반환
    // 결과가 없을 경우 빈 결과로 페이지 반환한
    public Page<UserResponseDto> findUser(String keyword, Integer pageNumber, Integer pageSize, String email) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("nickname"));
        log.info("user 검색");
        List<User> findByEmail = userRepository.findAllByEmailContainingAndIsDeletedIsFalseAndEmailNot(keyword, email);
        List<User> findByNickname = userRepository.findAllByNicknameContainingAndIsDeletedIsFalseAndEmailNot(keyword, email);
        List<User> mergedList = new ArrayList<>();
        mergedList.addAll(findByEmail);
        mergedList.addAll(findByNickname);

        // 중복제거 후 닉네임 기준으로 정렬
        List<User> distinctAndSorted = mergedList.stream()
                .distinct()
                .sorted(Comparator.comparing(User::getNickname))
                .collect(Collectors.toList());
        // paging 후 page<dto>로 변환
        Page<User> userPaged = new PageImpl<>(distinctAndSorted, pageable, distinctAndSorted.size());
        Page<UserResponseDto> userPagedResponseDto = userPaged.map(user -> UserResponseDto.fromEntity(user));
        return userPagedResponseDto;
    }

    // 회원정보 수정
    @Transactional
    public UserResponseDto updateUser(UserUpdateDto dto, String email) {
        User user = userUtils.getUser(email);
//        User user = getUser(email);
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
        User user = userUtils.getUser(email);
        log.info("비밀번호 수정: 비밀번호 입력 확인");
        if (!dto.getNewPassword().equals(dto.getPasswordCheck())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        user.updatePassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    // 비밀번호 인증
    public boolean verifyPassword(UserVerifyPwDto dto, String email) {
        log.info("현재 로그인한 사용자 비밀번호 인증");
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            log.info("login: 비밀번호 불일치");
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        return true;
    }

    // 회원탈퇴
    // 프로필 삭제, 즐겨찾기 삭제, 메이트 삭제
    @Transactional
    public void deleteUser(String email) {
        // 회원 확인
        User user = userUtils.getUser(email);
        // 프로필 정리
        profileService.deleteProfile(user);
        // 즐겨찾기 정리
        likesUserService.deleteLikesUserAll(user);
        // 메이트 정리
        matesService.deleteMateAll(user);
        // 회원 탈퇴
        log.info("{} 회원 탈퇴 완료", email);
        user.delete();
        userRepository.save(user);
    }

    public Boolean checkEmailDuplicated(String email) {
        log.info("{} 중복검사 existsByEmail: {}", email, userRepository.existsByEmail(email));
        return userRepository.existsByEmail(email);
    }
    public Boolean checkNickNameDuplicated(String nickname) {
        log.info("{} 중복검사 existsByEmail: {}", nickname, userRepository.existsByNickname(nickname));
        return userRepository.existsByNickname(nickname);
    }
}

