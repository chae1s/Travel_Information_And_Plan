package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.CommentRequestDto;
import com.example.Final_Project_9team.dto.CommentResponseDto;
import com.example.Final_Project_9team.dto.ResponseDto;
import com.example.Final_Project_9team.exception.SuccessCode;
import com.example.Final_Project_9team.repository.CommentRepository;
import com.example.Final_Project_9team.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("boards/{boardId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // comment 작성
    @PostMapping
    public ResponseEntity<CommentResponseDto> create(
            Authentication auth,
            @PathVariable("boardId") Long boardId,
            @RequestBody CommentRequestDto dto
            ) {

        return ResponseEntity.ok(
                commentService.create(auth.getName(), boardId, dto)
        );
    }

    // comment 수정
    @PutMapping("{commentId}")
    public ResponseEntity<CommentResponseDto> update(
            Authentication auth,
            @PathVariable("boardId") Long boardId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentRequestDto dto
    ) {
        return ResponseEntity.ok(
                commentService.update(auth.getName(), boardId, commentId, dto)
        );
    }

    // comment 삭제
    @DeleteMapping("{commentId}")
    public ResponseEntity<ResponseDto> delete(
            Authentication auth,
            @PathVariable("boardId") Long boardId,
            @PathVariable("commentId") Long commentId
    ) {
        commentService.delete(auth.getName(), boardId, commentId);
        return ResponseEntity.ok(
                ResponseDto.getMessage(SuccessCode.SUCCESS.getMessage())
        );
    }
}
