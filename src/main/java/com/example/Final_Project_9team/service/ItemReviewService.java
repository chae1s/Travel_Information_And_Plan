package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.ItemReviewRequestDto;
import com.example.Final_Project_9team.dto.ItemReviewResponseDto;
import com.example.Final_Project_9team.entity.ItemReview;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.entity.enums.Rating;
import com.example.Final_Project_9team.entity.item.Item;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.ItemRepository;
import com.example.Final_Project_9team.repository.ItemReviewRepository;
import com.example.Final_Project_9team.repository.UserRepository;
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
}
