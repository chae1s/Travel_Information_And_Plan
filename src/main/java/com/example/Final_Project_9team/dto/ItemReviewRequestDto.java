package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.ItemReview;
import com.example.Final_Project_9team.entity.enums.Rating;
import lombok.Data;

@Data
public class ItemReviewRequestDto {
    private Long id;
    private String nickname;
    private String content;
    private Rating rating;
    private Boolean isDeleted = false;

    public static ItemReviewRequestDto fromEntity(ItemReview itemReview) {
        ItemReviewRequestDto dto = new ItemReviewRequestDto();
        dto.setId(itemReview.getId());
        dto.setNickname(itemReview.getUser().getNickname());
        dto.setContent(itemReview.getContent());
        dto.setRating(itemReview.getRating());
        dto.setIsDeleted(itemReview.getIsDeleted());
        return dto;
    }
    public int getRatingValue() {
        return this.rating.getValue();
    }
}
