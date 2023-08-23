package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT a FROM Item a WHERE a.location.sido = :sido")
    Page<Item> findBySido(@Param("sido")String sido, Pageable pageable);
    @Query("SELECT a FROM Item a WHERE a.location.sido = :sido AND a.location.sigungu = :sigungu")
    Page<Item> findBySidoAndSigungu(@Param("sido")String sido, @Param("sigungu")String sigungu,Pageable pageable);
}
