package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.ItemReview;
import com.example.Final_Project_9team.entity.item.Item;
import com.example.Final_Project_9team.entity.item.Restaurant;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RestaurantResponseDto {
    private String contentId;
    private String contentTypeId;
    private String title;
    private Long createdTime;
    private Long modifiedTime;
    private String tel;
    private String homePage;
    private Long bookTour;
    private String firstImage;
    private String firstImage2;
    private String cpyrgtDivCd;
    private String upmyundong;
    private String sido;
    private String siGunGu;
    private String cat1;
    private String cat2;
    private String cat3;
    private String fullAddress;
    private Long zipCode;
    private String latitude;
    private String longitude;
    private Long mLevel;
    private String overView;
    private String restDate;
    private List<ItemReview> itemReviews;
    //--------------------------------
    private String firstMenu;
    private String openTimeFood;//영업시간
    private String packing;//포장가능여부
    private String parkingFood;//주차가능여부
    private String seat;//좌석수
    private String treatMenu;//취급 메뉴
    private String infoCenterFood;//문의 및 안내
    private String reservationFood;//예약안내
    private String lcnsNo;//인허가번호

    public static RestaurantResponseDto fromEntity(Restaurant restaurant) {
        return RestaurantResponseDto.builder()
                .title(restaurant.getName())
                .overView(restaurant.getOverView())
                .homePage(restaurant.getHomePage())
                .tel(restaurant.getTel())
                .firstImage(restaurant.getFirstImage())
                .firstImage2(restaurant.getFirstImage2())
                .cat1(restaurant.getCat1())
                .cat2(restaurant.getCat2())
                .sido(restaurant.getLocation().getSido())
                .siGunGu(restaurant.getLocation().getSigungu())
                .upmyundong(restaurant.getLocation().getUpmyundong())
                .fullAddress(restaurant.getLocation().getFullAddress())
                .latitude(restaurant.getLocation().getLatitude())
                .longitude(restaurant.getLocation().getLongitude())
                .contentId(restaurant.getContentId())
                .contentTypeId(restaurant.getContentTypeId())
                .itemReviews(restaurant.getItemReviews())
                //-----------------------------------------
                .firstMenu(restaurant.getFirstMenu())
                .openTimeFood(restaurant.getOpenTimeFood())
                .packing(restaurant.getPacking())
                .parkingFood(restaurant.getParkingFood())
                .seat(restaurant.getSeat())
                .treatMenu(restaurant.getTreatMenu())
                .infoCenterFood(restaurant.getInfoCenterFood())
                .reservationFood(restaurant.getReservationFood())
                .lcnsNo(restaurant.getLcnsNo())
                .build();
    }
}
