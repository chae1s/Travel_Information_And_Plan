package com.example.Final_Project_9team.entity.embeded;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
public class Location {
    private String sido;
    private String sigungu;
    private String upmyundong;
    private String latitude;
    private String longitude;
    private String fullAddress;

}
