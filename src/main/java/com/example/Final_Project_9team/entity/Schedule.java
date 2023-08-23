package com.example.Final_Project_9team.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer sumDistance;
    private Integer sumDuration;
    private Boolean display;

    @Builder.Default
    @OneToMany(mappedBy = "schedule")
    private List<ScheduleItem> scheduleItems = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "schedule")
    private List<ItemPath> itemPaths = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "schedule")
    private List<Mates> mates = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "schedule")
    private List<LikesSchedule> likesSchedules = new ArrayList<>();
}
