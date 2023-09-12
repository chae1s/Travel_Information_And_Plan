package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.Board;
import lombok.Data;

@Data
public class BoardListResponseDto {
    private Long boardId;
    private String title;
    private String username;
    private Integer viewCnt;
    private Integer likesCnt;

    public static BoardListResponseDto fromEntity(Board board, Integer likesCnt) {
        BoardListResponseDto dto = new BoardListResponseDto();
        dto.setBoardId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setUsername(board.getUser().getNickname());
        dto.setViewCnt(board.getViewCnt());
        dto.setLikesCnt(likesCnt);

        return dto;
    }
}
