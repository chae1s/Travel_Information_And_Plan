package com.example.Final_Project_9team.testmates;

import com.example.Final_Project_9team.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class SampleDataInitializer implements CommandLineRunner {
    private final MatesRepository matesRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public void run(String... args) throws Exception {
//         샘플 데이터 추가
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
//                User user4 = User.builder()
//                .email("user4@naver.com")
//                .nickname("user4")
//                .password("1234")
//                .role(Role.ROLE_USER)
//                .build();
//        userRepository.save(user4);
//        matesRepository.deleteAll();
//        for (Long i = 5L; i < 12L; i++) {
//            matesRepository.deleteById(i);
//        }
//        matesRepository.deleteAll();
//        chatRoomRepository.deleteAll();
//        chatMessageRepository.deleteAll();
//        scheduleRepository.deleteAll();
//        userRepository.deleteAll();
//
//        Schedule schedule = Schedule.builder()
//                .title("신나는 목포 여행")
//                .build();
//        scheduleRepository.save(schedule);

//        matesRepository.deleteById(2L);
//        matesRepository.deleteAll();
    }
}