package com.example.eodi.dto;

import lombok.Data;

@Data
public class CommentRequestDto {
    private String content;
    private Long parentId;
}
