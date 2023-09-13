package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Boolean existsByEmail(String email);
    Boolean existsByNickname(String nickname);

    List<User> findAllByEmailContainingAndIsDeletedIsFalseAndEmailNot(String keyword, String selfEmail);
    List<User> findAllByNicknameContainingAndIsDeletedIsFalseAndEmailNot(String keyword, String selfEmail);
}
