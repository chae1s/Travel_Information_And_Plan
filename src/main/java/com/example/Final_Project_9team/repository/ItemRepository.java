package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.Board;
import com.example.Final_Project_9team.entity.ItemReview;
import com.example.Final_Project_9team.entity.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    Page<Item> findAllByLikedItemsBySido(@Param("email") String email, @Param("sido") String sido, Pageable pageable);
    @Query("SELECT a FROM Item a WHERE a.location.sido = :sido")
    Page<Item> findBySido(@Param("sido")String sido, Pageable pageable);
    @Query("SELECT a FROM Item a WHERE a.location.sido = :sido")
    List<Item> findAllBySido(@Param("sido")String sido);
    @Query("SELECT a FROM Item a WHERE a.location.sido = :sido AND a.location.sigungu = :sigungu")
    Page<Item> findBySidoAndSigunguPage(@Param("sido")String sido, @Param("sigungu")String sigungu,Pageable pageable);

    @Query("SELECT a FROM Item a WHERE a.location.sido = :sido AND a.location.sigungu = :sigungu")
    List<Item> findBySidoAndSigungu(@Param("sido")String sido, @Param("sigungu")String sigungu);

    @Query("SELECT a FROM Item a WHERE a.location.sido = :sido AND a.location.sigungu = :sigungu AND a.contentTypeId = :contentTypeId")
    Page<Item> findBySidoAndSigunguAndContentTypePage(@Param("sido")String sido, @Param("sigungu")String sigungu, @Param("contentTypeId") String contentTypeId, Pageable pageable);

}
