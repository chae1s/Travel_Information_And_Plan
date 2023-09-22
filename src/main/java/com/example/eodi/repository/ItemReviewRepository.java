package com.example.eodi.repository;

import com.example.eodi.entity.ItemReview;
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
