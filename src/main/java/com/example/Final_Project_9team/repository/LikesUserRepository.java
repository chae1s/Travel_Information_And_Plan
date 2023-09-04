package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.LikesUser;
import com.example.Final_Project_9team.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesUserRepository extends JpaRepository<LikesUser, Long> {
    Optional<LikesUser> findByUserFromAndUserTo(User userFrom, User userTo);
}
