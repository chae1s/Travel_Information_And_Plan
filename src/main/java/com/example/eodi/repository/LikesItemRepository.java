package com.example.eodi.repository;

import com.example.eodi.entity.LikesItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikesItemRepository extends JpaRepository<LikesItem, Long> {
    @Query("SELECT i FROM LikesItem i WHERE i.user.email = :email")
    List<LikesItem> findLikesItemByUserId(@Param("email") String email);
}
