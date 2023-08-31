package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserEmailAndIsDeletedIsFalse(String email);
    Boolean existsByUserEmail(String email);

    Optional<Profile> findByUserIdAndIsDeletedIsFalse(Long userId);
}
