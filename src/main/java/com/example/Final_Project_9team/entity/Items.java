package com.example.Final_Project_9team.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;

import java.time.LocalTime;

@Entity
@Data
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contentId;
    //관광타입(12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점) ID
    private String contentTypeId;
    //item 이름
    private String title;
    //처음 등록날짜
    private String createdTime;
    //최근 수정날짜
    private String modifiedTime;
    //해당 item 전화번호, null값 허용
    private String tel;

    private String homePage;
    private String bookTour;
    private String firstImage;
    private String firstImage2;
    private String cpyrgtDivCd;
    //1:서울, 2:인천, 3:대전, 4:대구, 5:광주, 6:부산, 7:울산, 8:세종특별자치시, 31:경기도, 32:강원특별자치도
    //33:충정북도, 34:충청남도 35:경상북도, 36:경상남도, 37:전라북도, 38:전라남도, 39:제주도
    private String areaCode;
    //areaCode 별 시군구 관련 정보 엔티티
    //ex) areaCode=1, siGunGuCode=1 => 강남구
    //    areaCode=1, siGunGuCode=2 => 강동구
    private String siGunGuCode;
    private String cat1;
    private String cat2;
    private String cat3;
    private String addr1;
    private String addr2;
    private String zipCode;
    private String mapX;
    private String mapY;
    private String mLevel;
    private String overView;

}
