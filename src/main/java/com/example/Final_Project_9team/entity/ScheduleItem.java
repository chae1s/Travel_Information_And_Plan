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
public class ScheduleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer turn;
    private LocalDateTime tourDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Item item;

    @OneToOne(mappedBy = "departureScheduleItem")
    private ItemPath departure;
    @OneToOne(mappedBy = "arrivalScheduleItem")
    private ItemPath arrival;

}
