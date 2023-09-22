package com.example.eodi.entity;

import com.example.eodi.entity.base.BaseTimeEntity;
import com.example.eodi.entity.enums.Rating;
import com.example.eodi.entity.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemReview extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Item item;
    private String content;
    private Rating rating;
    private Boolean isDeleted;

    public void delete() {
        this.isDeleted = true;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
