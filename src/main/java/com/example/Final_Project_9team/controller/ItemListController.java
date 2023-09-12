package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.ItemPartResponseDto;
import com.example.Final_Project_9team.dto.ItemResponseDto;
import com.example.Final_Project_9team.dto.ResponseDto;
import com.example.Final_Project_9team.entity.ItemReview;
import com.example.Final_Project_9team.entity.item.Item;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.exception.SuccessCode;
import com.example.Final_Project_9team.repository.ItemRepository;
import com.example.Final_Project_9team.service.BookmarkItemService;
import com.example.Final_Project_9team.service.ItemListService;
import com.example.Final_Project_9team.service.ItemReviewService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;
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
    private final ItemReviewService itemReviewService;
    private final ItemRepository itemRepository;

    //모든 관광상품 조회
    @GetMapping("/all")
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
    @GetMapping("/{sido}/all")
    public ResponseEntity<List<ItemPartResponseDto>> readSidoAllItemList(
            @PathVariable("sido") String sido)
    {
        List<ItemPartResponseDto> itemDto = itemListService.readAllItemSidoPaged(sido);
        return ResponseEntity.ok(itemDto);
    }
    @GetMapping("/{sido}/{sigungu}/all")
    public ResponseEntity<List<ItemPartResponseDto>> readSidoSigunguAllItemList(
            @PathVariable("sido") String sido,
            @PathVariable("sigungu") String sigungu
    )
    {
        List<ItemPartResponseDto> itemDto = itemListService.readAllItemSidoSigunguPaged(sido, sigungu);
        return ResponseEntity.ok(itemDto);
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
    @GetMapping("/{sido}/{sigungu}/{contentTypeId}")
    public ResponseEntity<Page<ItemPartResponseDto>> readSidoSigunguContentTypeList(
            @PathVariable("sido") String sido,
            @PathVariable("sigungu") String sigungu,
            @PathVariable("contentTypeId") String contentTypeId,
            @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        return ResponseEntity.of(Optional.ofNullable(itemListService.readItemSidoAndSigunguAndContentType(page, sido, sigungu, contentTypeId)));
    }

    //관광상품 상세조회
    @GetMapping("/read/{itemId}")
    public ResponseEntity<?> readOneItem(
            @PathVariable("itemId") Long itemId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) throws IOException, ParseException, URISyntaxException {
        return ResponseEntity.ok(itemListService.readItem(itemId, page, size).getBody());
    }

    //즐겨찾기 기능
    @PostMapping("/add/{itemId}")
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


