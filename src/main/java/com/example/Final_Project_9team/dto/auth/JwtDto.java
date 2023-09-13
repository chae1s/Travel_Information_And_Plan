package com.example.Final_Project_9team.dto.auth;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtDto {
    private String token;
}
