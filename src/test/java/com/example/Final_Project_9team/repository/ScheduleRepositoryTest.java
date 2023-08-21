package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    public void setScheduleRepositoryIsNotNull() {
        assertThat(scheduleRepository).isNotNull();
    }

    @Test
    @DisplayName("일정 등록")
    public void createSchedule() {
        // given
        String title = "즐거운 여행";
        LocalDateTime startDate = LocalDateTime.of(2023, 8, 20, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 8, 25, 0, 0, 0);
        Schedule schedule = Schedule.builder()
                .title(title)
                .description("제주도")
                .startDate(startDate)
                .endDate(endDate)
                .build();

        // when
        schedule = scheduleRepository.save(schedule);

        // then
        assertThat(schedule.getId()).isNotNull();
        assertThat(schedule.getTitle()).isEqualTo(title);
        assertThat(schedule.getStartDate()).isEqualTo(startDate);
    }

}