package com.example.Final_Project_9team.dto;

import lombok.Data;

@Data
public class ItemPathDto {
    private Integer duration;
    private Integer distance;

    // 이름 정하기
    public static ItemPathDto getItemPath(Integer distance, Integer duration) {
        ItemPathDto dto = new ItemPathDto();

        dto.setDistance(distance);
        dto.setDuration(duration);

        return dto;
    }
}
