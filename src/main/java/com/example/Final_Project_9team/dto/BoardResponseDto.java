package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.Board;
import com.example.Final_Project_9team.entity.Comment;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class BoardResponseDto {
    private String title;
    private String content;
    private String username;
    private Integer viewCnt;
    private Integer likesCnt;
    private LocalDateTime createdAt;
    private PageDto<CommentResponseDto> comments;


    public static BoardResponseDto fromEntity(Board board, Page<Comment> comments, int likesCnt) {
        BoardResponseDto dto = new BoardResponseDto();
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setUsername(board.getUser().getNickname());
        dto.setViewCnt(board.getViewCnt());
        dto.setLikesCnt(likesCnt);
        dto.setCreatedAt(board.getCreatedAt());
        dto.setComments(
                PageDto.fromPage(
                        comments.map(CommentResponseDto::fromEntity)
                )
        );
        return dto;
    }

    public static BoardResponseDto fromEntity(Board board) {
        BoardResponseDto dto = new BoardResponseDto();
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setUsername(board.getUser().getNickname());
        dto.setViewCnt(board.getViewCnt());
        dto.setLikesCnt(0);
        dto.setCreatedAt(board.getCreatedAt());

        PageDto<CommentResponseDto> pageDto = new PageDto<>();
        pageDto.setLastPage(1);
        pageDto.setContent(new ArrayList<>());
        pageDto.setPageNumber(1);
        dto.setComments(pageDto);
        return dto;
    }
}
