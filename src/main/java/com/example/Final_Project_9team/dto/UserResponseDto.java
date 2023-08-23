package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.User;
import lombok.Data;
import lombok.Getter;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String profileImage;

    public static UserResponseDto fromEntity(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setNickname(user.getNickname());

        return dto;
    }
}
