package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.Mates;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class MatesResponseDto {
    private Long id;
    private Boolean isHost;
    private UserResponseDto userResponse;
    private LocalDate createdAt;
    private LocalDate modifiedAt;

    public MatesResponseDto(Mates mates) {
        this.id = mates.getId();
        this.isHost = mates.getIsHost();
        this.userResponse = new UserResponseDto(mates.getUser());
        this.createdAt = mates.getCreatedAt();
        this.modifiedAt = mates.getModifiedAt();
    }

}
