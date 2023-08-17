package com.example.Final_Project_9team.entity.embeded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private String sido;
    private String sigungu;
    private String upmyundong;
    private String latitude;
    private String longitude;
    private String fullAddress;

}
