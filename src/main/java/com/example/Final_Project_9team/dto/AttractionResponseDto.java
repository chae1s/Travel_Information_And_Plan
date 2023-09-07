package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.ItemReview;
import com.example.Final_Project_9team.entity.item.Attraction;
import com.example.Final_Project_9team.entity.item.Item;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AttractionResponseDto {
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
    //-----------------------------------
    private String accomCount;
    private String openDate;
    private String userTime;
    private String expAgeRange;
    private String expGuide;
    private String parking;
    private String infoCenter;
    private String userFee;
    private String chkPet;

    public static AttractionResponseDto fromEntity(Attraction attraction) {
        return AttractionResponseDto.builder()
                .title(attraction.getName())
                .overView(attraction.getOverView())
                .homePage(attraction.getHomePage())
                .tel(attraction.getTel())
                .firstImage(attraction.getFirstImage())
                .firstImage2(attraction.getFirstImage2())
                .cat1(attraction.getCat1())
                .cat2(attraction.getCat2())
                .sido(attraction.getLocation().getSido())
                .siGunGu(attraction.getLocation().getSigungu())
                .upmyundong(attraction.getLocation().getUpmyundong())
                .fullAddress(attraction.getLocation().getFullAddress())
                .latitude(attraction.getLocation().getLatitude())
                .longitude(attraction.getLocation().getLongitude())
                .contentId(attraction.getContentId())
                .contentTypeId(attraction.getContentTypeId())
                .itemReviews(attraction.getItemReviews())
                //----------------------------------------------
                .accomCount(attraction.getAccomCount())
                .openDate(attraction.getOpenDate())
                .userTime(attraction.getUserTime())
                .expAgeRange(attraction.getExpAgeRange())
                .expGuide(attraction.getExpGuide())
                .parking(attraction.getParking())
                .userFee(attraction.getUseFee())
                .chkPet(attraction.getChkPet())
                .build();
    }
}
