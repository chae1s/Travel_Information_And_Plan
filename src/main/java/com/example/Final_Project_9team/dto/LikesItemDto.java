package com.example.Final_Project_9team.dto;

import com.example.Final_Project_9team.entity.LikesItem;
import com.example.Final_Project_9team.entity.User;
import lombok.Data;

@Data
public class LikesItemDto {
    private Long id;
    private Long userId;
    private Long itemId;
    private String firstImage;
    private String fullAddress;
    private Boolean isLike;

    public static LikesItemDto fromEntity(LikesItem likesItem) {
        LikesItemDto dto = new LikesItemDto();
        dto.setId(likesItem.getId());
        dto.setItemId(likesItem.getItem().getId());
        dto.setFullAddress(likesItem.getItem().getLocation().getFullAddress());
        dto.setUserId(likesItem.getUser().getId());
        dto.setFirstImage(likesItem.getItem().getFirstImage());
        dto.setIsLike(likesItem.getIsLike());
        return dto;
    }

}
