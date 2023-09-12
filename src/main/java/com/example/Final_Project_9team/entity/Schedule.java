package com.example.Final_Project_9team.entity;

import com.example.Final_Project_9team.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
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
    private LocalDate startDate;
    private LocalDate endDate;
    private String sido;
    private Integer sumDistance;
    private Integer sumDuration;
    private Boolean display;
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

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

    @OneToOne(mappedBy = "schedule")
    private ChatRoom chatRoom;

    public void updateDisplay() {
        this.display = !this.display;
    }

    public void updateDurationAndDistance(Integer sumDuration, Integer sumDistance) {
        this.sumDuration = sumDuration;
        this.sumDistance = sumDistance;
    }

    public void updateSchedule(ScheduleRequestDto dto) {
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.sido = dto.getSido();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
    }
}
