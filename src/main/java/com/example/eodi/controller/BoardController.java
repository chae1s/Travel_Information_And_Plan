package com.example.eodi.controller;

import com.example.eodi.dto.*;
import com.example.eodi.exception.SuccessCode;
import com.example.eodi.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // board 작성
    @PostMapping
    public ResponseEntity<Long> create(
            Authentication auth,
            @RequestBody BoardRequestDto dto
    ) {
        return ResponseEntity.ok(
                boardService.create(auth.getName(), dto)
        );
    }

    // board 페이지 단위 조회
    @GetMapping
    public ResponseEntity<PageDto<BoardListResponseDto>> readAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                boardService.readAll(page, size)
        );
    }

    // board 상세 조회
    @GetMapping("{boardId}")
    public ResponseEntity<BoardResponseDto> readOne(
            @PathVariable("boardId") Long boardId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                boardService.readOne(boardId,page,size)
        );
    }

    // board 수정
    @PutMapping("{boardId}")
    public ResponseEntity<ResponseDto> update(
            Authentication auth,
            @PathVariable("boardId") Long boardId,
            @RequestPart(value = "dto") BoardRequestDto dto
    ) throws FileNotFoundException {
        boardService.update(auth.getName(), boardId, dto);
        return ResponseEntity.ok(
                ResponseDto.getMessage(SuccessCode.SUCCESS.getMessage())
        );
    }

    // board 삭제
    @DeleteMapping("{boardId}")
    public ResponseEntity<ResponseDto> delete(
            Authentication auth,
            @PathVariable("boardId") Long boardId
    ) throws FileNotFoundException {
        boardService.delete(auth.getName(), boardId);
        return ResponseEntity.ok(
                ResponseDto.getMessage(SuccessCode.SUCCESS.getMessage())
        );
    }
}
