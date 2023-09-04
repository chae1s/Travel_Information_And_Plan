package com.example.Final_Project_9team.dto.user;

import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Long id;
    private String username; // email
    private String password;
    private String nickname;
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 현재 유저당 부여되는 권한은 하나임으로 하나만 추가함
        // 여러 Role 부여시 User의 Role 필드 수정 필요
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    // 사용자 식별 정보는 로그인 email
    @Override
    public String getUsername() {
        return this.username;
    }

    public Long getUserId() {
        return this.id;
    }

    // 사용하지 않는 기능으로, 전부 true 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static CustomUserDetails fromEntity(User user) {
        return CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .id(id)
                .nickname(nickname)
                .password(password)
                .email(username)
                .role(role)
                .build();
    }


}
