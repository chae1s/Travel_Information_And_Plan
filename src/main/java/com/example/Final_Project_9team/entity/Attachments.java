package com.example.Final_Project_9team.entity;


import com.example.Final_Project_9team.entity.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attachments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;
    // 확장자
    private String extension;
    private Boolean isDeleted;
    // 파일사이즈 : mb
    private Float size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Item item;

    public void delete() {
        this.isDeleted = true;
        this.board = null;
        this.item = null;
    }
}
