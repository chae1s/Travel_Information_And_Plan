package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.FileDto;
import com.example.Final_Project_9team.dto.ProfileDto;
import com.example.Final_Project_9team.dto.auth.JwtDto;
import com.example.Final_Project_9team.dto.user.*;
import com.example.Final_Project_9team.entity.Profile;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.entity.enums.Role;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.global.jwt.JwtTokenUtils;
import com.example.Final_Project_9team.repository.ProfileRepository;
import com.example.Final_Project_9team.repository.UserRepository;
import com.example.Final_Project_9team.utils.FileHandler;
import com.example.Final_Project_9team.utils.UserUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final FileHandler fileHandler;
    //    private final ProfileService profileService;
    private final CustomUserDetailsManager manager;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final LikesUserService likesUserService;
    private final MatesService matesService;
    private final UserUtils userUtils;

    // 회원등록
    @Transactional
    public void registerUser(UserSignupDto dto) {
        log.info("회원가입: 비밀번호 입력 확인");
        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
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

        log.info("회원가입: 기본프로필 생성");
        profileRepository.save(Profile.builder().user(user).build());
//        profileService.createProfile(user);

        log.info("회원가입 완료 pk:{}, email:{}, nickname:{}",
                user.getId(), user.getEmail(), user.getNickname());
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
        return UserResponseDto.fromEntityAll(user);
    }

    // 회원 검색
    // keyword가 포함된 email, nickname으로 회원 검색, 탈퇴회원 및 본인을 제외하고 반환
    // 해당 회원이 없을 경우 빈 리스트 반환
    public List<UserResponseDto> findUser(String keyword, String userEmail) {
        log.info("user 검색: 검색어 \"{}\"", keyword);
        // 검색어가 없을 경우 예외 (모든 회원이 조회되는 것 방지)
        if (keyword == ""){
            throw new CustomException(ErrorCode.ERROR_NO_KEYWORD);
        }
        List<User> findByEmail = userRepository.findAllByEmailContainingAndIsDeletedIsFalseAndEmailNot(keyword, userEmail);
        List<User> findByNickname = userRepository.findAllByNicknameContainingAndIsDeletedIsFalseAndEmailNot(keyword, userEmail);
        List<User> mergedList = new ArrayList<>();
        mergedList.addAll(findByEmail);
        mergedList.addAll(findByNickname);

        // 중복제거 후 닉네임 기준으로 정렬
        List<UserResponseDto> userResponseDtoList = mergedList.stream()
                .distinct()
                .sorted(Comparator.comparing(User::getNickname))
                .map(UserResponseDto::fromEntity)
                .collect(Collectors.toList());
        return userResponseDtoList;
    }

    // 회원정보 수정
    @Transactional
    public void updateUser(UserUpdateDto dto, MultipartFile profileImage, String email) {
        User user = userUtils.getUser(email);
        log.info("회원정보: 사용자 확인 \"{}\"", user.getEmail());
        String newNickname = dto.getNickname();
        log.info("회원정보 수정: 사용자 기존 닉네임 \"{}\"", user.getNickname());
        // 새로운 닉네임과 기존 닉네임이 다를 경우 중복확인 수행 후 기존 user 객체에 반영
        if (!newNickname.equals(user.getNickname())) {
            log.info("회원정보 수정: \"{}\" 닉네임 중복 여부 확인", newNickname);
            checkNickNameDuplicated(dto.getNickname());
            user.updateUser(newNickname);
//            if (userRepository.existsByNickname(dto.getNickname())) {
//                throw new CustomException(ErrorCode.ALREADY_EXISTED_NICKNAME);
//            }
        }

        // 프로필 수정

        Profile profile = user.getProfile();

        // 프로필이 존재하는지 확인
        if (profile == null) {
            log.info("error: 프로필이 존재하지 않음");
            throw new CustomException(ErrorCode.ERROR_NOT_FOUND);
        }

        log.info("프로필: 내용 수정");
        profile.update(dto.getProfileContent(), dto.getProfileLocation());

        log.info("프로필: 이미지 수정");
        // 파일이 제대로 첨부되어 전달되었는지 확인, null이라면 확인 필요
//        if (profileImage == null) {
//            log.info("profileImage == null");
//            throw new CustomException(ErrorCode.INVALID_FILE);
//        }

        log.info("dto:" + dto);
        // 파일이 첨부되었다면 기존 이미지 삭제 후 추가
        log.info("profileImage:" + profileImage);

        if (profileImage != null) {
            log.info("프로필: 새 프로필 이미지 등록");
            try {
                fileHandler.deleteFile(profile.getProfileImage());
            } catch (FileNotFoundException e) {
                log.info("FileNotFoundException");
            }
            FileDto profileImageDto = fileHandler.saveFile(profileImage);
            profile.updateImage(profileImageDto.getPath());
            // 파일이 첨부되어 있지 않고, 삭제 요청이라면,
        } else if (dto.isImageDefault() == true) {
            log.info("프로필: 기존 프로필 삭제 후 기본 프로필로 변경");
            try {
                fileHandler.deleteFile(profile.getProfileImage());
            } catch (FileNotFoundException e) {
                log.info("FileNotFoundException");
                profile.updateImage(null);
            }
        } else {
            log.info("프로필: 프로필 이미지 유지");
        }
        userRepository.save(user);
        log.info("회원정보: 수정완료");
        profileRepository.save(profile);
        log.info("프로필: 수정완료");

        // 확인용 로직
        log.info(user.getProfile().toString());
        log.info(profile.toString());
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

    // 회원탈퇴
    // 프로필 삭제, 즐겨찾기 삭제, 메이트 삭제
    @Transactional
    public void deleteUser(String email) {
        // 회원 확인
        User user = userUtils.getUser(email);

        // 프로필 정리
        Profile profile = user.getProfile();
        // 프로필이 존재하는지 확인
        if (profile == null) {
            throw new CustomException(ErrorCode.ERROR_NOT_FOUND);
        }
        String filePath = profile.getProfileImage();
        // 프로필 이미지 파일 삭제
        try {
            fileHandler.deleteFile(filePath);
        } catch (FileNotFoundException e) {
            log.info("프로필: 삭제할 파일이 존재하지 않습니다.");
        }
        // 프로필 엔티티 변경
        profile.delete();
        profileRepository.save(profile);

        // 즐겨찾기 정리
        likesUserService.deleteLikesUserAll(user);

        // 메이트 정리
        matesService.deleteMateAll(user);

        // 회원 탈퇴
        user.delete();
        userRepository.save(user);
        log.info("{} 회원 탈퇴 완료", email);
    }

    public Boolean checkEmailDuplicated(String email) {
        log.info("{} 중복검사 existsByEmail: {}", email, userRepository.existsByEmail(email));
        return userRepository.existsByEmail(email);
    }

    public Boolean checkNickNameDuplicated(String nickname) {
        log.info("{} 중복검사 existsBynickname: {}", nickname, userRepository.existsByNickname(nickname));
        return userRepository.existsByNickname(nickname);
    }
}

