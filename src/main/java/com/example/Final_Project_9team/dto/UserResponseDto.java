package com.example.Final_Project_9team.dto;


import com.example.Final_Project_9team.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    private String email;
    private String nickname;
    private String role;

}
