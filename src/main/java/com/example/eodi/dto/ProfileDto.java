package com.example.eodi.dto;
// 프로필만 업로드 하는 로직, 사용하지 않음
import com.example.eodi.entity.Profile;
import lombok.Data;

@Data
public class ProfileDto {
    private String content;
    private String profileImage;
    private String location;

    public static ProfileDto fromEntity(Profile profile){
        ProfileDto dto = new ProfileDto();
        if (profile.getProfileImage() == null) {
            dto.setProfileImage("/img/default-profile.png");
        }
        else {
            dto.setProfileImage("/media/" + profile.getProfileImage());
        }
        dto.setContent(profile.getContent());
        dto.setLocation(profile.getLocation());
        return dto;
    }
}
