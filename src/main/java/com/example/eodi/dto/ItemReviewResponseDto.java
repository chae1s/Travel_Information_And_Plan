package com.example.eodi.dto;

import com.example.eodi.entity.ItemReview;
import com.example.eodi.entity.enums.Rating;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemReviewResponseDto {
    private String content;
    private String username;
    private Long id;
    private LocalDateTime createdAt;
    private Rating rating;
    private String title;
    public static ItemReviewResponseDto fromEntity(ItemReview itemReview) {
        ItemReviewResponseDto dto = new ItemReviewResponseDto();
        dto.setContent(itemReview.getContent());
        dto.setUsername(itemReview.getUser().getNickname());
        dto.setId(itemReview.getId());
        dto.setCreatedAt(itemReview.getCreatedAt());
        dto.setRating(itemReview.getRating());
        dto.setTitle(itemReview.getItem().getName());
        return dto;
    }
}
