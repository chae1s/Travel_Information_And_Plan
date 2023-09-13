package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.entity.LikesItem;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.entity.item.Item;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.ItemRepository;
import com.example.Final_Project_9team.repository.BookmarkItemRepository;
import com.example.Final_Project_9team.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookmarkItemService {
    private final ItemRepository itemRepository;
    private final BookmarkItemRepository bookmarkItemRepository;
    private final UserRepository userRepository;

    //즐겨찾기 추가 혹은 삭제 메소드
    public void createOrDeleteBookmarkItem(String email, Long itemId) {
        Optional<LikesItem> optionalLikesItem = bookmarkItemRepository.findByUser_EmailAndItem_Id(email, itemId);
        if(optionalLikesItem.isPresent()) {
            LikesItem BookmarkItem = optionalLikesItem.get();
            if(BookmarkItem.getIsLike()) {
                BookmarkItem.updateLikesItem(false);
            } else {
                BookmarkItem.updateLikesItem(true);
                bookmarkItemRepository.save(BookmarkItem);
            }
        } else {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
            Item item = itemRepository.findById(itemId).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

            LikesItem newLikesItem = LikesItem.builder()
                    .user(user)
                    .item(item)
                    .isLike(true)
                    .build();
            bookmarkItemRepository.save(newLikesItem);
        }
    }

}
