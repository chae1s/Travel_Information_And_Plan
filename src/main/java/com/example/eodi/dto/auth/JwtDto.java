package com.example.eodi.dto.auth;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtDto {
    private String token;
}
