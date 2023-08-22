package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.item.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
