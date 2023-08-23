package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.ItemPartResponseDto;
import com.example.Final_Project_9team.dto.ItemResponseDto;
import com.example.Final_Project_9team.service.ItemListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("item-list")
public class ItemListController {
    private final ItemListService itemListService;
    //모든 관광상품 조회
    @GetMapping
    public ResponseEntity<Page<ItemPartResponseDto>> readAllItemList(
            @RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok(itemListService.readItemAllPaged(page));
    }
    //시도별 관광상품 조회
    @GetMapping("/{sido}")
    public ResponseEntity<Page<ItemPartResponseDto>> readSidoItemList(
            @PathVariable("sido") String sido,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.of(Optional.ofNullable(itemListService.readItemSidoPaged(page, sido)));
    }
    //시도 + 시군구 별 관광상품 조회
    @GetMapping("/{sido}/{sigungu}")
    public ResponseEntity<Page<ItemPartResponseDto>> readSidoSigunguItemList(
            @PathVariable("sido") String sido,
            @PathVariable("sigungu") String sigungu,
            @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        return ResponseEntity.of(Optional.ofNullable(itemListService.readItemSidoAndSigungu(page, sido, sigungu)));
    }
    //관광상품 상세조회
    @GetMapping("/read/{itemId}")
    public ResponseEntity<?> readOneItem(
            @PathVariable("itemId") Long itemId
    ) {
        return itemListService.readItem(itemId);
    }
}
