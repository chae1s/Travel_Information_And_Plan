package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.item.Item;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemResponseDto { //아이템 상세정보 조회에 필요한 Dto
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

    public static ItemResponseDto fromEntity(Item item) {
        return ItemResponseDto.builder()
                .title(item.getName())
                .overView(item.getOverView())
                .homePage(item.getHomePage())
                .tel(item.getTel())
                .firstImage(item.getFirstImage())
                .firstImage2(item.getFirstImage2())
                .cat1(item.getCat1())
                .cat2(item.getCat2())
                .sido(item.getLocation().getSido())
                .siGunGu(item.getLocation().getSigungu())
                .upmyundong(item.getLocation().getUpmyundong())
                .fullAddress(item.getLocation().getFullAddress())
                .latitude(item.getLocation().getLatitude())
                .longitude(item.getLocation().getLongitude())
                .contentId(item.getContentId())
                .contentTypeId(item.getContentTypeId())
                .build();
    }
}
