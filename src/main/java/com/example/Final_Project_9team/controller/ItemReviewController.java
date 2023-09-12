package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.ItemReviewRequestDto;
import com.example.Final_Project_9team.dto.ItemReviewResponseDto;
import com.example.Final_Project_9team.dto.ResponseDto;
import com.example.Final_Project_9team.entity.ItemReview;
import com.example.Final_Project_9team.exception.SuccessCode;
import com.example.Final_Project_9team.service.ItemReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item-detail/read/{itemId}")
@RequiredArgsConstructor
public class ItemReviewController {
    private final ItemReviewService itemReviewService;

    @PostMapping
    public ResponseEntity<ResponseDto> createItemReview(
            Authentication auth,
            @PathVariable("itemId") Long itemId,
            @RequestBody ItemReviewRequestDto dto
            ) {
        itemReviewService.createItemReview(auth.getName(), itemId, dto, dto.getRatingValue());
        return ResponseEntity.ok(
                ResponseDto.getMessage(SuccessCode.CREATED.getMessage())
        );
    }
    @PutMapping("/reviews/{itemReviewId}")
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
    @DeleteMapping("/reviews/{itemReviewId}")
    public ResponseEntity<ResponseDto> deleteItemReview(
            Authentication auth,
            @PathVariable("itemId") Long itemId,
            @PathVariable("itemReviewId") Long itemReviewId
    ) {
        itemReviewService.deleteItemReview(auth.getName(), itemId, itemReviewId);
        return ResponseEntity.ok(
                ResponseDto.getMessage(SuccessCode.SUCCESS.getMessage())
        );
    }
    @GetMapping("/reviews")
    public ResponseEntity<List<ItemReviewRequestDto>> getItemReviews(@PathVariable("itemId") Long itemId) {
        List<ItemReviewRequestDto> itemReviews = itemReviewService.getItemReviews(itemId);
        return ResponseEntity.ok(itemReviews);
    }
}
