package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.FileDto;
import com.example.Final_Project_9team.dto.ProfileDto;
import com.example.Final_Project_9team.entity.Profile;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.ProfileRepository;
import com.example.Final_Project_9team.utils.FileHandler;
import com.example.Final_Project_9team.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

//@Slf4j
//@Service
//@RequiredArgsConstructor
public class ProfileService {

    // UserService로 통합, 더이상 사용하지 않는 Service

//    private final FileHandler fileHandler;
//    private final ProfileRepository profileRepository;
//    private final UserUtils userUtils;
//
//    public void createProfile(User user) {
//        log.info("회원가입: 기본프로필 생성");
//        profileRepository.save(Profile.builder().user(user).build());
//    }
//
//    // 본인 프로필 조회
//    public ProfileDto readMyProfile(String email) {
//        log.info("프로필: 사용자 프로필 조회");
//        Profile profile = profileRepository.findByUserEmailAndIsDeletedIsFalse(email).orElseThrow(
//                () -> new CustomException(ErrorCode.ERROR_NOT_FOUND));
//        return ProfileDto.fromEntity(profile);
//    }
//
//    // 다른 회원의 프로필 조회
//    public ProfileDto readProfile(Long userId) {
//        log.info("프로필: 사용자 프로필 조회");
//        Profile profile = profileRepository.findByUserIdAndIsDeletedIsFalse(userId).orElseThrow(
//                () -> new CustomException(ErrorCode.ERROR_NOT_FOUND));
//        return ProfileDto.fromEntity(profile);
//    }
//
//
//    // 프로필 수정
//    public void updateProfile(ProfileDto dto, MultipartFile profileImage, String email) {
//        User user = userUtils.getUser(email);
//        Profile profile = user.getProfile();
//        // 프로필이 존재하는지 확인
//        if (profile == null) {
//            throw new CustomException(ErrorCode.ERROR_NOT_FOUND);
//        }
//        log.info("프로필: 내용 수정");
//        profile.update(dto.getContent(), dto.getLocation());
//
//        // 파일이 제대로 첨부되어 전달되었는지 확인, null이라면 다시 첨부 필요
//        log.info("프로필: 프로필 이미지 설정");
//        if (profileImage == null) {
//            throw new CustomException(ErrorCode.INVALID_FILE);
//        }
//        // 이미지가 첨부되어 있다면 파일을 저장하고 Porfile에 path 저장
//        if (!profileImage.isEmpty()) {
//            // 기존 파일을 삭제
//            try {
//                fileHandler.deleteFile(profile.getProfileImage());
//            } catch (FileNotFoundException e) {
//                log.info("프로필: 삭제할 파일이 존재하지 않습니다.");
//            }
//            // 새 파일을 저장
//            FileDto profileImageDto = fileHandler.saveFile(profileImage);
//            profile.updateImage(profileImageDto.getPath());
//        }
//        profileRepository.save(profile);
//        log.info("프로필: 수정완료");
//    }
//
//    // 프로필 삭제
//    // 한번 생성된 프로필은 삭제할 수 없고, 회원 탈퇴시 삭제처리 됨.
//    // 프로필 이미지 삭제, 프로필 profileImage == null, isDeleted == true
//    public void deleteProfile(User user) {
//
//        Profile profile = user.getProfile();
//        // 프로필이 존재하는지 확인
//        if (profile == null) {
//            throw new CustomException(ErrorCode.ERROR_NOT_FOUND);
//        }
//        String filePath = profile.getProfileImage();
//        // 프로필 이미지 파일 삭제
//        try {
//            fileHandler.deleteFile(filePath);
//        } catch (FileNotFoundException e) {
//            log.info("프로필: 삭제할 파일이 존재하지 않습니다.");
//        }
//        // 프로필 엔티티 변경
//        profile.delete();
//        profileRepository.save(profile);
//    }

}
