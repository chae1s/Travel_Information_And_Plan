package com.example.eodi.repository;

import com.example.eodi.entity.LikesUser;
import com.example.eodi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesUserRepository extends JpaRepository<LikesUser, Long> {
    Optional<LikesUser> findByUserFromAndUserTo(User userFrom, User userTo);
}
