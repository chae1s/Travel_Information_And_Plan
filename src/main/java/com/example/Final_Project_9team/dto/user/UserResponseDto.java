package com.example.Final_Project_9team.dto.user;

import com.example.Final_Project_9team.entity.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.annotation.JsonInclude;

@Slf4j
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String profileImage;
    private String content;
    private String location;
    private Boolean isLikedByMe;
    private Boolean isInvited;

    // 프로필 이미지 경로 지정 메서드
    // 프로필 이미지가 있는 경우와 없는 경우 확인해서 fromEnetity 메서드에서 사용
    private String checkProfileImage(String profileImage) {
        if (profileImage != null) {
            // 차후 외부 경로 지정시 수정
            log.info("profileImage url: {}", "/media/" + profileImage);
            return "/media/" + profileImage;
        } else {
            return "/img/default-profile.png";
        }
    }

    // 회원 기본 정보 조회 - id, email, nickname, profileImage
    public static UserResponseDto fromEntity(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setNickname(user.getNickname());
        dto.setProfileImage(dto.checkProfileImage(user.getProfile().getProfileImage()));
        return dto;
    }

    // 프로필 포함 모든 정보 조회
    public static UserResponseDto fromEntityAll(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setNickname(user.getNickname());
        dto.setContent(user.getProfile().getContent());
        dto.setLocation(user.getProfile().getLocation());
        dto.setProfileImage(dto.checkProfileImage(user.getProfile().getProfileImage()));
        return dto;
    }

    // 회원 목록 조회시 id, email, nickname, profileImage,isLiked 포함
    // 만약 목록에서 isLiked로 상태를 표시하고 싶을 경우 사용
    public static UserResponseDto fromEntity(User user, Boolean isLiked) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setNickname(user.getNickname());
        dto.setIsLikedByMe(isLiked);
        dto.setProfileImage(dto.checkProfileImage(user.getProfile().getProfileImage()));
        return dto;
    }

}

