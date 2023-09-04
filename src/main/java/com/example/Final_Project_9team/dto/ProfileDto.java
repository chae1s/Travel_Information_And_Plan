package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.Profile;
import lombok.Data;

@Data
public class ProfileDto {
    private String content;
    private String profileImage;
    private String location;

    public static ProfileDto fromEntity(Profile profile){
        ProfileDto dto = new ProfileDto();
        dto.setProfileImage("/static/" + profile.getProfileImage());
        dto.setContent(profile.getContent());
        dto.setLocation(profile.getLocation());
        return dto;
    }
}
