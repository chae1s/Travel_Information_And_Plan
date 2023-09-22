package com.example.eodi.entity;

import com.example.eodi.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemPath extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer turn;
    private Integer distance;
    private Integer duration;
    private LocalDate tourDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Schedule schedule;

    @OneToOne
    @JoinColumn
    private ScheduleItem departureScheduleItem;

    @OneToOne
    @JoinColumn
    private ScheduleItem arrivalScheduleItem;
}