package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.item.Item;
import lombok.Data;

@Data
public class ItemListResponseDto {
    private Long id;
    private String name;
    private String firstImage;
    private String sido;
    private String fullAddress;
    private String longitude;
    private String latitude;
    private String category;

    public static ItemListResponseDto fromEntity(Item item) {
        ItemListResponseDto dto = new ItemListResponseDto();
        dto.setId(item.getId());
        dto.setFullAddress(item.getLocation().getFullAddress());
        dto.setLongitude(item.getLocation().getLongitude());
        dto.setLatitude(item.getLocation().getLatitude());
        dto.setFirstImage(item.getFirstImage());
        dto.setName(item.getName());
        dto.setSido(item.getLocation().getSido());
        String category = switch (item.getCat1()) {
            case "A01" -> "자연";
            case "A02" -> "문화/예술/역사";
            case "A03" -> "레포츠";
            case "A04" -> "쇼핑";
            case "A05" -> "음식";
            case "B02" -> "숙박";
            case "C01" -> "추천코스";
            default -> "기타";
        };
        dto.setCategory(category);

        return dto;
    }
}
