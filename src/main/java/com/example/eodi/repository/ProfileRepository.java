package com.example.eodi.repository;

import com.example.eodi.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserEmailAndIsDeletedIsFalse(String email);
    Boolean existsByUserEmail(String email);

    Optional<Profile> findByUserIdAndIsDeletedIsFalse(Long userId);
}
