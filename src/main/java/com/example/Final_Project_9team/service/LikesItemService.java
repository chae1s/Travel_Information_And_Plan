package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.entity.LikesItem;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.entity.item.Item;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.ItemRepository;
import com.example.Final_Project_9team.repository.LikesItemRepository;
import com.example.Final_Project_9team.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikesItemService {
    private final ItemRepository itemRepository;
    private final LikesItemRepository likesItemRepository;
    private final UserRepository userRepository;

    //즐겨찾기 추가 혹은 삭제 메소드
    public void createOrDeleteLikeItem(Long userId, Long itemId) {
        Optional<LikesItem> optionalLikesItem = likesItemRepository.findByUser_IdAndItem_Id(userId, itemId);
        if(optionalLikesItem.isPresent()) {
            LikesItem likesItem = optionalLikesItem.get();
            if(likesItem.getIsLike() == true) {
                likesItem.updateLikesItem(false);
            } else {
                likesItem.updateLikesItem(true);
                likesItemRepository.save(likesItem);
            }
        } else {
            User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
            Item item = itemRepository.findById(itemId).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

            LikesItem newLikesItem = LikesItem.builder()
                    .user(user)
                    .item(item)
                    .isLike(true)
                    .build();
            likesItemRepository.save(newLikesItem);
        }
    }

}
