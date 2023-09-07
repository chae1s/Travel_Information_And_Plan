package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.ItemReview;
import com.example.Final_Project_9team.entity.item.Accommodation;
import com.example.Final_Project_9team.entity.item.Item;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccommodationResponseDto {
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
    //------------------------------------------------------
    private String info;
    private String roomType;
    private String roomCapacity;
    private String reservationUrl;
    private String parkingLodging;
    private String infoCenterLodging;
    private String checkInTime;
    private String checkOutTime;

    public static AccommodationResponseDto fromEntity(Accommodation accommodation) {
        return AccommodationResponseDto.builder()
                .title(accommodation.getName())
                .overView(accommodation.getOverView())
                .homePage(accommodation.getHomePage())
                .tel(accommodation.getTel())
                .firstImage(accommodation.getFirstImage())
                .firstImage2(accommodation.getFirstImage2())
                .cat1(accommodation.getCat1())
                .cat2(accommodation.getCat2())
                .sido(accommodation.getLocation().getSido())
                .siGunGu(accommodation.getLocation().getSigungu())
                .upmyundong(accommodation.getLocation().getUpmyundong())
                .fullAddress(accommodation.getLocation().getFullAddress())
                .latitude(accommodation.getLocation().getLatitude())
                .longitude(accommodation.getLocation().getLongitude())
                .contentId(accommodation.getContentId())
                .contentTypeId(accommodation.getContentTypeId())
                .itemReviews(accommodation.getItemReviews())
                //------------------------------------
                .info(accommodation.getInfo())
                .roomType(accommodation.getRoomType())
                .roomCapacity(accommodation.getRoomCapacity())
                .reservationUrl(accommodation.getReservationUrl())
                .parkingLodging(accommodation.getParkingLodging())
                .infoCenterLodging(accommodation.getInfoCenterLodging())
                .checkInTime(accommodation.getCheckInTime())
                .checkOutTime(accommodation.getCheckOutTime())
                .build();
    }
}
