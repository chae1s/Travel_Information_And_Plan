package com.example.eodi.dto;

import com.example.eodi.dto.user.UserResponseDto;
import com.example.eodi.entity.Mates;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatesResponseDto {
    private Long id;
    private Boolean isHost;
    private UserResponseDto userResponse;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static MatesResponseDto fromEntity(Mates mates) {
        MatesResponseDto dto = new MatesResponseDto();
        dto.setId(mates.getId());
        dto.setIsHost(mates.getIsHost());
        dto.setUserResponse(UserResponseDto.fromEntity(mates.getUser()));
        dto.setCreatedAt(mates.getCreatedAt());
        dto.setModifiedAt(mates.getModifiedAt());

        return dto;
    }

}
