package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.Board;
import com.example.Final_Project_9team.entity.Comment;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardResponseDto {
    private String title;
    private String content;
    private String username;
    private Integer viewCnt;
    private Integer likesCnt;
    private LocalDateTime createdAt;
    private List<String> imageUrls;
    private PageDto<CommentResponseDto> comments;


    public static BoardResponseDto fromEntity(Board board, Page<Comment> comments, int likesCnt) {
        BoardResponseDto dto = new BoardResponseDto();
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setUsername(board.getUser().getNickname());
        dto.setViewCnt(board.getViewCnt());
        dto.setLikesCnt(likesCnt);
        dto.setCreatedAt(board.getCreatedAt());
        dto.setImageUrls(board.getAttachments().stream()
                .map(attachments -> "/" + attachments.getPath())
                .toList());
        dto.setComments(
                PageDto.fromPage(
                        comments.map(CommentResponseDto::fromEntity)
                )
        );
        return dto;
    }
}
