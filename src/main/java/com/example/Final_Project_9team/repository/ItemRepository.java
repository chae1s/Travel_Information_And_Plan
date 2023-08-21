package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
