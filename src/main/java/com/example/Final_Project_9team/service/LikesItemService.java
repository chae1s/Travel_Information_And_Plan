package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.entity.LikesItem;
import com.example.Final_Project_9team.entity.enums.YesOrNo;
import com.example.Final_Project_9team.repository.LikesItemRepository;

import java.util.Optional;

public class LikesItemService {
    private final LikesItemRepository likesItemRepository;

    public LikesItemService(LikesItemRepository likesItemRepository) {
        this.likesItemRepository = likesItemRepository;
    }

//    public Long createOrDeleteLikeItem(Long userId, Long itemId) {
//        Optional<LikesItem> optionalLikesItem = likesItemRepository.findByUser_IdAndItem_Id(userId, itemId);
//        if(optionalLikesItem.isPresent()) {
//            LikesItem likesItem = optionalLikesItem.get();
//            if(likesItem.getIsLike() == true) {
//                likesItem.
//            }
//        }
//    }

}
