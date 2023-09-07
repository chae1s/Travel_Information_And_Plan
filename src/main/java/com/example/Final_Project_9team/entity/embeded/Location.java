package com.example.Final_Project_9team.entity.embeded;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@SuperBuilder
public class Location {
    //1:서울, 2:인천, 3:대전, 4:대구, 5:광주, 6:부산, 7:울산, 8:세종특별자치시, 31:경기도, 32:강원특별자치도
    //33:충정북도, 34:충청남도 35:경상북도, 36:경상남도, 37:전라북도, 38:전라남도, 39:제주도
    private String sido;//areacode
    private String sigungu;//sigungucode
    private String upmyundong; //addr2
    private String latitude;//위도 -> mapy 좌표를 위도로 변환해야함, 지도 api에서는 어떤 정보를 필요로 하는지 확인해야함
    private String longitude;//경도 -> mapx 좌표를 경도로 변환해야함
    private String fullAddress; //addr1의 예시 : 충청남도 부여군 규암면 백제문로 388
    private String mLevel;//Map Level 응답(필요한지는 잘 모르겠지만 일단 map 관련된 변수같아보여 위도 경도 변수와 같은 곳에 둠)

}
