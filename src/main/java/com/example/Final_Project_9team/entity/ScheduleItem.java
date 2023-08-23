package com.example.Final_Project_9team.entity;

import com.example.Final_Project_9team.entity.item.Item;
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
public class ScheduleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer turn;
    private LocalDate tourDate;


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
