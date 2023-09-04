package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.enums.Rating;
import lombok.Data;

@Data
public class ItemReviewRequestDto {
    private String content;
    private Rating rating;
}
