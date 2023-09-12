package com.example.Final_Project_9team.config;

import com.example.Final_Project_9team.global.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/static/**", "/js/**", "/css/**", "/img/**",
                        "/media/**" // 외부 정적자원 접근 경로
                        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authHttp -> authHttp
                        .requestMatchers(
                                // common
                                "/index.html",
                                "/", "/error",

                                "/users/register", "users/check/**",
                                "users/info/**",
                                "board-create",
                                "/item-list/**", "/item-detail/**",
                                "/schedules/write/**","/users/me/**","schedules/**",
                                // yj
                                "schedules/chat/**", "/chatting","/mypage","/chatting","schedules/chat/**",
                                "api/user/search/**","/users"

                                )
                        .permitAll()
                        .requestMatchers(
                                "/users/login"
                        )
                        .anonymous()
//                        .requestMatchers("/users/roleUser").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtTokenFilter, AuthorizationFilter.class)

        ;
        return http.build();
    }

}
