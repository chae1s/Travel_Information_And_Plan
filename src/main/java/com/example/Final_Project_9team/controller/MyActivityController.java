package com.example.Final_Project_9team.controller;


import com.example.Final_Project_9team.dto.*;
import com.example.Final_Project_9team.exception.SuccessCode;
import com.example.Final_Project_9team.service.MyActivityService;
import com.example.Final_Project_9team.service.ScheduleService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("users/me")
// 내 활동 페이지에서 조회할 것들
public class MyActivityController {
    private final MyActivityService myActivityService;

    // 작성한 board 페이지 단위 조회
    @GetMapping("boards")
    public ResponseEntity<PageDto<BoardListResponseDto>> readAllBoards(
            Authentication auth,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                myActivityService.readAllBoards(auth.getName(), page, size)
        );
    }

    // board 좋아요 하기
    @PostMapping("liked-boards/{boardId}")
    public ResponseEntity<ResponseDto> like(
            Authentication auth,
            @PathVariable("boardId") Long boardId
    ) {
        myActivityService.likeBoard(auth.getName(), boardId);
        return ResponseEntity.ok(
                ResponseDto.getMessage(SuccessCode.SUCCESS.getMessage())
        );
    }

    // 좋아요 한 board 페이지 단위 조회
    @GetMapping("liked-boards")
    public ResponseEntity<PageDto<BoardListResponseDto>> readAllLikedBoards(
            Authentication auth,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                myActivityService.readAllLikedBoards(auth.getName(), page, size)
        );
    }

    // 댓글 쓴 board 페이지 단위 조회
    @GetMapping("comments")
    public ResponseEntity<PageDto<BoardListResponseDto>> readAllCommentedBoards(
            Authentication auth,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                myActivityService.readAllCommentedBoards(auth.getName(), page, size)
        );
    }

    // 내가 포함된 schedule 페이지 단위 조회
    @GetMapping("schedules")
    public ResponseEntity<PageDto<ScheduleListResponseDto>> readAllSchedules(
            Authentication auth,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                myActivityService.readAllSchedules(auth.getName(), page, size)
        );
    }

    // 좋아요 한 schedule 페이지 단위 조회
    @GetMapping("liked-schedules")
    public ResponseEntity<PageDto<ScheduleListResponseDto>> readAllLikedSchedules(
            Authentication auth,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                myActivityService.readAllLikedSchedules(auth.getName(), page, size)
        );
    }

    // 좋아요 한 item 페이지 단위 조회
    @GetMapping("liked-items")
    public ResponseEntity<PageDto<ItemListResponseDto>> readAllLikedItems(
            Authentication auth,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                myActivityService.readAllLikedItems(auth.getName(), page, size)
        );
    }

    // 나의 일정 중 날짜 기준으로 목록 조회하기
    @GetMapping("schedules/after-day")
    public List<ScheduleListResponseDto> readSchedulesAfterToday(Authentication auth) {

        return myActivityService.readSchedulesAfterToday(auth.getName());

    }

    @GetMapping("liked-items/{sido}")
    public ResponseEntity<Page<ItemListResponseDto>> readLikedItemsBySido(Authentication auth,
                                                                          @PathVariable("sido") String sido,
                                                                          @RequestParam(value = "page", defaultValue = "1") int page,
                                                                          @RequestParam(value = "size", defaultValue = "7") int size) {

        return ResponseEntity.ok(myActivityService.readLikedItemsBySido(auth.getName(), sido, page, size));
    }

    @GetMapping("/schedules/{scheduleId:\\d+}")
    public ResponseEntity<ScheduleResponseDto> readSchedule(@PathVariable Long scheduleId, Authentication auth) {

        return ResponseEntity.ok(myActivityService.readSchedule(scheduleId, auth.getName()));
    }
}
