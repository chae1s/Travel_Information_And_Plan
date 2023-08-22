package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String profileImage;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
