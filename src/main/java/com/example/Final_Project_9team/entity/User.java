package com.example.Final_Project_9team.entity;

import com.example.Final_Project_9team.entity.base.BaseTimeEntity;
import com.example.Final_Project_9team.entity.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean isDeleted;

    @OneToOne(mappedBy = "user")
    private Profile profile;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Schedule> schedules = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<ItemReview> itemReviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Mates> mates = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<LikesSchedule> likesSchedules = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<LikesItem> likesItems = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<LikesBoard> likesBoards = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "userFrom")
    private List<LikesUser> fromUsers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "userTo")
    private List<LikesUser> toUsers = new ArrayList<>();

    public void updateUser(String newNickname) {
        this.nickname = newNickname;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
