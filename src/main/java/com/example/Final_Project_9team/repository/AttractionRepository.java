package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.item.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
}
