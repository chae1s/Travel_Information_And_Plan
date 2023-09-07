package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.ItemReviewRequestDto;
import com.example.Final_Project_9team.dto.ItemReviewResponseDto;
import com.example.Final_Project_9team.dto.ResponseDto;
import com.example.Final_Project_9team.exception.SuccessCode;
import com.example.Final_Project_9team.service.ItemReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/read/{itemId}/review")
@RequiredArgsConstructor
public class ItemReviewController {
    private final ItemReviewService itemReviewService;

    @PostMapping
    public ResponseEntity<ResponseDto> createItemReview(
            Authentication auth,
            @PathVariable("itemId") Long itemId,
            @RequestBody ItemReviewRequestDto dto
            ) {
        itemReviewService.createItemReview(auth.getName(), itemId, dto, dto.getRating());
        return ResponseEntity.ok(
                ResponseDto.getMessage(SuccessCode.CREATED.getMessage())
        );
    }
    @PutMapping("{itemReviewId}")
    public ResponseEntity<ResponseDto> updateItemReview(
            Authentication auth,
            @PathVariable("itemId") Long itemId,
            @PathVariable("itemReviewId") Long itemReviewId,
            @RequestBody ItemReviewRequestDto dto
    ) {
        itemReviewService.updateItemReview(auth.getName(), itemId, itemReviewId, dto, dto.getRating());
        return ResponseEntity.ok(
                ResponseDto.getMessage(SuccessCode.SUCCESS.getMessage())
        );
    }
    @DeleteMapping("{itemReviewId}")
    public ResponseEntity<ResponseDto> deleteItemReview(
            Authentication auth,
            @PathVariable("itemId") Long itemId,
            @PathVariable("itemReviewId") Long itemReviewId,
            @RequestBody ItemReviewRequestDto dto
    ) {
        itemReviewService.deleteItemReview(auth.getName(), itemId, itemReviewId);
        return ResponseEntity.ok(
                ResponseDto.getMessage(SuccessCode.SUCCESS.getMessage())
        );
    }
}
