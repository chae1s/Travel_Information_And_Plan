package com.example.eodi.repository;

import com.example.eodi.entity.User;
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
