package com.example.Final_Project_9team.mates;

import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.entity.enums.Role;
import com.example.Final_Project_9team.etc.ScheduleRepository;
import com.example.Final_Project_9team.etc.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class SampleDataInitializer implements CommandLineRunner {
    private final MatesRepository matesRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public void run(String... args) throws Exception {
        // 샘플 데이터 추가
//        userRepository.deleteAll();
//
//        User user = User.builder()
//                .email("user123@naver.com")
//                .nickname("user1")
//                .password("1234")
//                .role(Role.ROLE_USER)
//                .build();
//        userRepository.save(user);
//        User user2 = User.builder()
//                .email("dani123@naver.com")
//                .nickname("user2")
//                .password("1234")
//                .role(Role.ROLE_USER)
//                .build();
//        userRepository.save(user2);
//                User user3 = User.builder()
//                .email("u3u3@naver.com")
//                .nickname("user3")
//                .password("1234")
//                .role(Role.ROLE_USER)
//                .build();
//        userRepository.save(user3);

//
//        Schedule schedule = Schedule.builder()
//                .title("신나는 목포 여행")
//                .build();
//        scheduleRepository.save(schedule);

    }
}