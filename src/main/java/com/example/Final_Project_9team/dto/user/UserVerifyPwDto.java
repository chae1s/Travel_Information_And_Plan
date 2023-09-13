package com.example.Final_Project_9team.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserVerifyPwDto {
    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String password;
}
