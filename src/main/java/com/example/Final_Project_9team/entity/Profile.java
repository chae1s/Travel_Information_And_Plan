package com.example.Final_Project_9team.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@SuperBuilder
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Builder.Default
    private String content = "'한마디'";
//    @Builder.Default
    private String profileImage;
//    private String profileImage = "default-profile.png";
    @Builder.Default
    private String location = "'여행을 떠나고 돌아오는 곳'";
    @Builder.Default
    private Boolean isDeleted = false;

    @OneToOne
    @JoinColumn
    private User user;

    public void update(String content, String location){
        this.content = content;
        this.location = location;
    }

    public void updateImage(String profileImage){
        this.profileImage = profileImage;
    }

    public void delete(){
        this.isDeleted = true;
        this.profileImage = null;
    }


}
