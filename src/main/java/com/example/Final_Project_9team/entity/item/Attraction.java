package com.example.Final_Project_9team.entity.item;

import com.example.Final_Project_9team.entity.item.Item;
import jakarta.persistence.*;
import lombok.Data;

//관광지(contentTypeId = 12)
@Data
@Entity
@DiscriminatorValue("Attraction")
public class Attraction extends Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이 부분을 추가합니다.
    private Long id;
    private Long charge;//contentType=12 인 관광지에는 없음 contentType=14 인 문화시설에 존재
    private String accomCount;// 수용인원
    private String openDate; //개장일
    private String userTime;// 이용시간
    private String expAgeRange;//체험가능 연령
    private String expGuide;//체험 안내
    private String parking;//주차시설
    private String infoCenter;//문의 및 안내
    private String useFee;//이용요금
    private String chkPet;//애완동물 동반가능 정보, 문화시설은 chkPetCulture
}
