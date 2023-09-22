package com.example.eodi.dto;

import com.example.eodi.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private String content;
    private String username;
    private Long id;
    private Long parentId;
    private LocalDateTime createdAt;

    public static CommentResponseDto fromEntity(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setContent(comment.getContent());
        dto.setUsername(comment.getUser().getNickname());
        dto.setId(comment.getId());
        dto.setParentId(comment.getParentId());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }
}
