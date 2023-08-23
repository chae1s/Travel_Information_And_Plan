package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.Mates;
import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MatesRepositoryTest {

    @Autowired
    private MatesRepository matesRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    public void matesRepositoryIsNotNull() {
        assertThat(matesRepository).isNotNull();
        assertThat(scheduleRepository).isNotNull();
    }

    @Test
    @DisplayName("일정 작성자 등록")
    public void createMates() {

        // given
        // 로그인한 유저의
        User user = User.builder()
                .id(1L)
                .build();

        // 로그인한 유저가 만드는 일정
        String title = "즐거운 여행";
        LocalDate startDate = LocalDate.of(2023, 8, 20);
        LocalDate endDate = LocalDate.of(2023, 8, 25);
        Schedule schedule = Schedule.builder()
                .id(1L)
                .title(title)
                .description("제주도")
                .startDate(startDate)
                .endDate(endDate)
                .build();

        schedule = scheduleRepository.save(schedule);

        Mates mates = Mates.builder()
                .user(user)
                .schedule(schedule)
                .isHost(true)       // 일정을 만든 유저이므로 호스트는 true
                .isAccepted(true)   // 일정을 만든 유저이므로 초대 수락 여부는 true
                .isDeleted(false)
                .build();

        // when
        mates = matesRepository.save(mates);

        // then
        assertThat(mates.getSchedule().getId()).isEqualTo(schedule.getId());
        assertThat(mates.getUser().getId()).isEqualTo(user.getId());


    }

}