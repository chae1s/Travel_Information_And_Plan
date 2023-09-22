package com.example.eodi.service;

import com.example.eodi.dto.ItemReviewRequestDto;
import com.example.eodi.dto.ItemReviewResponseDto;
import com.example.eodi.entity.ItemReview;
import com.example.eodi.entity.User;
import com.example.eodi.entity.enums.Rating;
import com.example.eodi.entity.item.Item;
import com.example.eodi.exception.CustomException;
import com.example.eodi.exception.ErrorCode;
import com.example.eodi.repository.ItemRepository;
import com.example.eodi.repository.ItemReviewRepository;
import com.example.eodi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemReviewService {
    private final ItemReviewRepository itemReviewRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public void createItemReview(String email, Long itemId, ItemReviewRequestDto dto, int ratingValue) {
        User writer = userRepository.findByEmail(email).get();
        Item item = itemRepository.findById(itemId).get();

        // ratingValue를 Rating 열거형으로 변환
        Rating rating = Rating.values()[ratingValue];

        ItemReview newItemReview = ItemReview.builder()
                .content(dto.getContent())
                .user(writer)
                .item(item)
                .rating(rating)
                .isDeleted(false)
                .build();
        itemReviewRepository.save(newItemReview);
    }
    public List<ItemReviewRequestDto> getItemReviews(Long itemId) {
        List<ItemReview> itemReviews = itemReviewRepository.findByItemId(itemId);
        List<ItemReviewRequestDto> itemReviewRequestDtos = new ArrayList<>();
        for (ItemReview itemReview : itemReviews) {
            itemReviewRequestDtos.add(ItemReviewRequestDto.fromEntity(itemReview));
        }
        return itemReviewRequestDtos;
    }
    public void updateItemReview(String email, Long itemId, Long itemReviewId, ItemReviewRequestDto dto, Rating rating) {
        User writer = userRepository.findByEmail(email).get();
        Item item = itemRepository.findById(itemId).get();
        ItemReview itemReview = itemReviewRepository.findByIdAndIsDeletedFalse(itemReviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.ERROR_NOT_FOUND));
        if (!writer.equals(itemReview.getUser())) {
            throw new CustomException(ErrorCode.USER_NO_AUTH);
        }
        itemReview.updateContent(dto.getContent());
        itemReviewRepository.save(itemReview);
    }
    public void deleteItemReview(String email, Long itemId, Long itemReviewId) {
        User writer = userRepository.findByEmail(email).get();
        ItemReview itemReview = itemReviewRepository.findByIdAndIsDeletedFalse(itemReviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.ERROR_NOT_FOUND));
        if (!writer.equals(itemReview.getUser())) {
            throw new CustomException(ErrorCode.USER_NO_AUTH);
        }
        itemReview.delete();
        itemReviewRepository.save(itemReview);
    }
    public List<ItemReviewResponseDto> getItemReviewsByUserId(String email) {
        if(!userRepository.existsByEmail(email)) {
                throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }List<ItemReview> itemReviews = itemReviewRepository.findItemReviewByUserId(email);
        List<ItemReviewResponseDto> itemReviewResponseDtos = new ArrayList<>();

        for(ItemReview itemReview : itemReviews) {
            itemReviewResponseDtos.add(ItemReviewResponseDto.fromEntity(itemReview));
        }
        return itemReviewResponseDtos;
    }
}
