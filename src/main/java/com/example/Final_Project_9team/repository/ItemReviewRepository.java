package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.ItemReview;
import com.example.Final_Project_9team.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemReviewRepository extends JpaRepository<ItemReview, Long> {
    Optional<ItemReview> findByIdAndIsDeletedFalse(Long itemReviewId);
    List<ItemReview> findByItemId(Long itemId);
    @Query("SELECT i FROM ItemReview i WHERE i.user.email = :email")
    List<ItemReview> findItemReviewByUserId(@Param("email") String email);
}
