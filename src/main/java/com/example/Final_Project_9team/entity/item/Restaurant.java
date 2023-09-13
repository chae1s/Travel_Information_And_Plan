package com.example.Final_Project_9team.entity.item;

import com.example.Final_Project_9team.entity.enums.RestaurantCategory;
import jakarta.persistence.*;
import lombok.Data;

//39:음식점
@Data
@Entity
@DiscriminatorValue("Restaurant")
public class Restaurant extends Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이 부분을 추가합니다.
    private Long id;
    private RestaurantCategory restaurantCategory; //정보없음
    private String firstMenu;//대표 메뉴
    private String openTimeFood;//영업시간
    private String packing;//포장가능여부
    private String parkingFood;//주차가능여부
    private String seat;//좌석수
    private String treatMenu;//취급 메뉴
    private String infoCenterFood;//문의 및 안내
    private String reservationFood;//예약안내
    private String lcnsNo;//인허가번호

}
