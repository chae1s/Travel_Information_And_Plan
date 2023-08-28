package com.example.Final_Project_9team.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserUpdatePwDto {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}",
            message = "비밀번호는 8자리 이상 15자리 이하로 영문 대,소문자와 숫자, 특수기호를 포함하여 작성해주세요.")
    private String newPassword;
    @NotBlank(message = "비밀번호를 다시 한번 입력해주세요.")
    private String passwordCheck;
}
