package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.Board;
import com.example.Final_Project_9team.entity.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(
            "SELECT i FROM Item i " +
                    "INNER JOIN i.likesItems li " +
                    "WHERE " +
                    "li.user.email = :email " +
                    "AND li.isLike = true " +
                    "ORDER BY li.id DESC "
    )
    Page<Item> findAllLikedItemsByMe(@Param("email") String email, Pageable pageable);

    @Query(
            "SELECT i FROM Item i " +
                    "INNER JOIN i.likesItems li " +
                    "WHERE " +
                    "li.user.email = :email " +
                    "AND i.location.sido = :sido " +
                    "AND li.isLike = true " +
                    "ORDER BY li.id DESC "
    )
    Page<Item> findByLikedItemsBySido(@Param("email") String email, @Param("sido") String sido, Pageable pageable);
}
