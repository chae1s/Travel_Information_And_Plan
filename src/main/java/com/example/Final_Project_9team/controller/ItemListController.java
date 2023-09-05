package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.ItemPartResponseDto;
import com.example.Final_Project_9team.dto.ItemResponseDto;
import com.example.Final_Project_9team.dto.ResponseDto;
import com.example.Final_Project_9team.entity.item.Item;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.exception.SuccessCode;
import com.example.Final_Project_9team.repository.ItemRepository;
import com.example.Final_Project_9team.service.BookmarkItemService;
import com.example.Final_Project_9team.service.ItemListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("item-list")
public class ItemListController {
    private final ItemListService itemListService;
    private final BookmarkItemService bookmarkItemService;
    private final ItemRepository itemRepository;

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
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "4") int pageSize) {
        return ResponseEntity.of(Optional.ofNullable(itemListService.readItemSidoPaged(page, sido, pageSize)));
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

//    @GetMapping("/read/{itemId}")
//    public String readOneItem(
//            @PathVariable("itemId") Long itemId,
//            Model model
//    ) throws IOException {
//        ResponseEntity<?> itemData = itemListService.readItem(itemId);
//        model.addAttribute("itemData", itemData);
//        model.addAttribute("itemId", itemId);
//        return "detail-info";
//    }
    @GetMapping("/read/{itemId}")
    public ResponseEntity<?> readOneItem(
            @PathVariable("itemId") Long itemId
    ) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("detail-info");
        modelAndView.addObject("item",itemListService.readItem(itemId));
        modelAndView.addObject("itemId", itemId);
        return itemListService.readItem(itemId);
    }

    //즐겨찾기 기능
    @PostMapping("/{itemId}")
    public ResponseEntity<ResponseDto> bookmarkItem(
            Authentication auth,
            @PathVariable("itemId") Long itemId
    ) {
        bookmarkItemService.createOrDeleteBookmarkItem(auth.getName(), itemId);
        return ResponseEntity.ok(
                ResponseDto.getMessage(SuccessCode.SUCCESS.getMessage())
        );
    }
}


