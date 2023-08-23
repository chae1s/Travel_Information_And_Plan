package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.*;
import com.example.Final_Project_9team.entity.item.Accommodation;
import com.example.Final_Project_9team.entity.item.Attraction;
import com.example.Final_Project_9team.entity.item.Item;
import com.example.Final_Project_9team.entity.item.Restaurant;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.AccommodationRepository;
import com.example.Final_Project_9team.repository.AttractionRepository;
import com.example.Final_Project_9team.repository.ItemRepository;
import com.example.Final_Project_9team.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemListService {
    private final ItemRepository itemRepository;
    private final AttractionRepository attractionRepository;
    private final AccommodationRepository accommodationRepository;
    private final RestaurantRepository restaurantRepository;

    private static final int PAGE_SIZE = 5;
    //TODO : 전체 리스트 조회, 페이징
    public Page<ItemPartResponseDto> readItemAllPaged(int page) {
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by("id"));
        Page<Item> itemPage = itemRepository.findAll(pageable);
        return itemPage.map(ItemPartResponseDto::fromEntity);
    }
    //TODO : 시도별 아이템 조회 (서울, 경기, 인천, 부산, 등등)
    public Page<ItemPartResponseDto> readItemSidoPaged(int page, String sido) {
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by("id"));
        Page<Item> itemPage = itemRepository.findBySido(sido, pageable);

        return itemPage.map(ItemPartResponseDto::fromEntity);
    }
    //TODO : 시 + 구 별 아이템 조회
    public Page<ItemPartResponseDto> readItemSidoAndSigungu(int page, String sido, String sigungu) {
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by("id"));
        Page<Item> itemPage = itemRepository.findBySidoAndSigungu(sido, sigungu, pageable);

        return itemPage.map(ItemPartResponseDto::fromEntity);
    }
    //TODO: 상세정보 조회
    public ResponseEntity<?> readItem(Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if(optionalItem.isPresent()) {
            Item item = optionalItem.get();
            String contentTypeId = item.getContentTypeId();

            switch(contentTypeId) {
                case "12":
                case "14":
                    Optional<Attraction> optionalAttraction = attractionRepository.findById(itemId);
                    Attraction attraction = optionalAttraction.get();
                    AttractionResponseDto attractionResponseDto = AttractionResponseDto.fromEntity(attraction);
                    return ResponseEntity.ok(attractionResponseDto);
                case "32":
                    Optional<Accommodation> optionalAccommodation = accommodationRepository.findById(itemId);
                    Accommodation accommodation = optionalAccommodation.get();
                    AccommodationResponseDto accommodationResponseDto = AccommodationResponseDto.fromEntity(accommodation);
                    return ResponseEntity.ok(accommodationResponseDto);
                case "39":
                    Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(itemId);
                    Restaurant restaurant = optionalRestaurant.get();
                    RestaurantResponseDto restaurantResponseDto = RestaurantResponseDto.fromEntity(restaurant);
                    return ResponseEntity.ok(restaurantResponseDto);
                default:
                    throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
            }

        } else throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
    }
}
