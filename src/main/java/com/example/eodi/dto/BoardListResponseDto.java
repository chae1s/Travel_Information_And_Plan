package com.example.eodi.dto;

import com.example.eodi.entity.Board;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListResponseDto {
    private Long boardId;
    private String title;
    private String username;
    private Integer viewCnt;
    private Integer likesCnt;
    private LocalDateTime createdAt;

    public static BoardListResponseDto fromEntity(Board board, Integer likesCnt) {
        BoardListResponseDto dto = new BoardListResponseDto();
        dto.setBoardId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setUsername(board.getUser().getNickname());
        dto.setViewCnt(board.getViewCnt());
        dto.setCreatedAt(board.getCreatedAt());
        dto.setLikesCnt(likesCnt);

        return dto;
    }
}
