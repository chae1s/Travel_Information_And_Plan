package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.Mates;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MatesResponseDto {
    private Long id;
    private Boolean isHost;
    private UserResponseDto userResponseDto;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MatesResponseDto(Mates mates) {
        this.id = mates.getId();
        this.isHost = mates.getIsHost();
        this.userResponseDto = new UserResponseDto(mates.getUser());
        this.createdAt = mates.getCreatedAt();
        this.modifiedAt = mates.getModifiedAt();
    }

}
