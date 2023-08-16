package com.example.Final_Project_9team.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private Integer viewCnt;
    private Boolean isDeleted;
    private Category category;

    private Boolean isLike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<Attachments> attachments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<LikesBoard> likesBoards = new ArrayList<>();
}
