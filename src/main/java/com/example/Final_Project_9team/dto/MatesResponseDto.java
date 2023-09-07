package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.dto.user.UserResponseDto;
import com.example.Final_Project_9team.entity.Mates;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
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
