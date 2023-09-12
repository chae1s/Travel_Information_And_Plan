package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.ItemReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemReviewRepository extends JpaRepository<ItemReview, Long> {
    Optional<ItemReview> findByIdAndIsDeletedFalse(Long itemReviewId);
    List<ItemReview> findByItemId(Long itemId);
}
