package com.example.Final_Project_9team.dto;

import lombok.Data;

@Data
public class CommentRequestDto {
    private String content;
    private Long parentId;
}
