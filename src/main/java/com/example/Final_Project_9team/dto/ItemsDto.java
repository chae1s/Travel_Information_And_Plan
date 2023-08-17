package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.Items;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemsDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentId;

    private Long contentTypeId;
    private String title;
    private Long createdTime;
    private Long modifiedTime;
    private String tel;
    private String telName;
    private String homePage;
    private Long bookTour;
    private String firstImage;
    private String firstImage2;
    private String cpyrgtDivCd;
    private Long areaCode;
    private Long siGunGuCode;
    private String cat1;
    private String cat2;
    private String cat3;
    private String addr1;
    private String addr2;
    private Long zipCode;
    private String mapX;
    private String mapY;
    private Long mLevel;
    private String overView;

//    public static ItemsDto fromEntity(Items entity) {
//        return ItemsDto.builder()
//                .
//    }
}
