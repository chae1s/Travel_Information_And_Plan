package com.example.Final_Project_9team.etc;

import com.example.Final_Project_9team.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByNickname(String nickname);
}
