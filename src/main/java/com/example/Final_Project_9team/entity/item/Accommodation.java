package com.example.Final_Project_9team.entity.item;

import jakarta.persistence.*;
import lombok.Data;

//32:숙박
@Data
@Entity
@DiscriminatorValue("Accommodation")
public class Accommodation extends Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이 부분을 추가합니다.
    private Long id;
    private String info;
    private String roomType;// ex) 스탠다드 더블/트윈, 디럭스 더블 ...
    private String roomCapacity;//roomcount: 객실 수
    private String reservationUrl;//예약안내 홈페이지
    private String parkingLodging; //주차 가능여부
    private String infoCenterLodging;//문의 및 안내
    private String checkInTime; //체크인
    private String checkOutTime;//체크아웃


}
