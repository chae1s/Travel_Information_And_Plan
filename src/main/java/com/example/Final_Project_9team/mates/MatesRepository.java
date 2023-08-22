package com.example.Final_Project_9team.mates;

import com.example.Final_Project_9team.entity.Mates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatesRepository extends JpaRepository<Mates,Long> {
}
