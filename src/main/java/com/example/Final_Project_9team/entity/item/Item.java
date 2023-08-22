package com.example.Final_Project_9team.entity.item;

import com.example.Final_Project_9team.entity.Attachments;
import com.example.Final_Project_9team.entity.ItemReview;
import com.example.Final_Project_9team.entity.LikesItem;
import com.example.Final_Project_9team.entity.ScheduleItem;
import com.example.Final_Project_9team.entity.base.BaseTimeEntity;
import com.example.Final_Project_9team.entity.embeded.Location;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {
    //createdTime -> createdAt, modifiedTime -> modifiedAt
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //title
    private String name;
    private String contentId;
    private String contentTypeId;
    //정보 없음
    private String openTime;// Attraction에 이용시간, 숙박테이블에는 체크인 체크아웃 시간정도?
    //해당 관광지 홈페이지 정보
    private String homePage;
    private String firstImage; //대표이미지(원본), URL응답
    private String firstImage2; //대표이미지(썸네일), URL응답
    private String cat1;//A01:자연, A02:인문(문화/예술/역사), A03:레포츠, A04:쇼핑, A05:음식, B02:숙박, C01:추천코스
    private String cat2;
    private String cat3;
    //저작권 유형 -> Type1:제1유형(출처표시-권장) Type3:제3유형(제1유형 + 변경금지)
    private String cpyrgtDivCd;//필요한지는 모르겠지만 넣어둠
    private String bookTour;//교과서 속 여행지 여부 이것도 필요한지는 아직 모르겠습니다
    private String overView;//개요 혹은 설명?
    private String restDate;// 쉬는 날 / 음식점 : restDateFood, 문화시설: restDateCulture, 관광지: restDate
    private String tel;

    @Embedded
    private Location location;

    @Builder.Default
    @OneToMany(mappedBy = "item")
    private List<ScheduleItem> scheduleItems = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "item")
    private List<ItemReview> itemReviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "item")
    private List<Attachments> attachments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "item")
    private List<LikesItem> likesItems = new ArrayList<>();
}
