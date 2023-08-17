package com.example.Final_Project_9team.entity.item;

import com.example.Final_Project_9team.entity.Attachments;
import com.example.Final_Project_9team.entity.ItemReview;
import com.example.Final_Project_9team.entity.LikesItem;
import com.example.Final_Project_9team.entity.ScheduleItem;
import com.example.Final_Project_9team.entity.embeded.Location;
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
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String openTime;

    @Embedded
    private Location location;

    @Builder.Default
    @OneToMany(mappedBy = "item")
    private List<ScheduleItem> scheduleItems = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "item")
    private List<ItemReview> itemReviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "item")
    private List<Attachments> attachments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "item")
    private List<LikesItem> likesItems = new ArrayList<>();
}
