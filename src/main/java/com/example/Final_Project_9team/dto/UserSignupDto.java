package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSignupDto {
    @Email(message = "이메일 형식으로 입력해주세요") @NotBlank
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}",
            message = "비밀번호는 8자리 이상 15자리 이하로 영문 대,소문자와 숫자, 특수기호를 포함하여 작성해주세요.")
    private String password;
    @NotBlank(message = "비밀번호를 다시 한번 입력해주세요.")
    private String passwordCheck;
    @NotBlank
    @Size(max = 12, message = "닉네임은 한글자 이상 열두글자 이하로 작성해주세요.")
    private String nickname;
    private Role role;

}
