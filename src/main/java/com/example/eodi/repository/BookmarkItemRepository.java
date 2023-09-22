package com.example.eodi.repository;

import com.example.eodi.entity.LikesItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkItemRepository extends JpaRepository<LikesItem, Long> {
    Optional<LikesItem> findByUser_EmailAndItem_Id(String email, Long itemId);
}
