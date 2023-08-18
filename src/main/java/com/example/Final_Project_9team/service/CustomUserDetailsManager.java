package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.dto.CustomUserDetails;
import com.example.Final_Project_9team.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsManager implements UserDetailsManager {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));
        return CustomUserDetails.fromEntity(user);
    }

    @Override
    public void createUser(UserDetails user) {
        log.info("\"{}\" 회원정보 생성");
        userRepository.save(((CustomUserDetails) user).toEntity());
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
