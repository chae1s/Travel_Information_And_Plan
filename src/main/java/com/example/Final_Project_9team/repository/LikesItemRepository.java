package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.LikesItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesItemRepository extends JpaRepository<LikesItem, Long> {
    Optional<LikesItem> findByUser_IdAndItem_Id(Long userId, Long itemId);
}
