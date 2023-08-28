package com.example.Final_Project_9team.dto.user;

import com.example.Final_Project_9team.entity.LikesUser;
import com.example.Final_Project_9team.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String profileImage;
    private Boolean isLikedByMe;


    // 회원 정보 조회
    public static UserResponseDto fromEntity(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setNickname(user.getNickname());
//        dto.setProfileImage(user.getProfile().getProfileImage());
        return dto;
    }

    // 회원 목록 조회
    public static UserResponseDto fromEntity(User user, Boolean isLiked) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setNickname(user.getNickname());
        dto.setIsLikedByMe(isLiked);

        return dto;
    }

}

