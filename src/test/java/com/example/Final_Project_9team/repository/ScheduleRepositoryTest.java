package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void setScheduleRepositoryIsNotNull() {
        assertThat(scheduleRepository).isNotNull();
    }

    @Test
    @DisplayName("일정 등록")
    public void createSchedule() {
        // given
        String title = "즐거운 여행";
        LocalDate startDate = LocalDate.of(2023, 8, 20);
        LocalDate endDate = LocalDate.of(2023, 8, 25);
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

    @Test
    @DisplayName("여행 마지막 날짜가 오늘 날짜 이후에 있는 일정 목록")
    public void readSchedulesAfterToday() {
        String title = "즐거운 여행";
        LocalDate startDate = LocalDate.of(2023, 8, 20);
        LocalDate endDate = LocalDate.of(2023, 8, 22);

        LocalDate today = LocalDate.of(2023, 8, 21);

        User user = userRepository.findById(1L).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Schedule schedule = Schedule.builder()
                .title(title)
                .description("제주도")
                .user(user)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        scheduleRepository.save(schedule);

        // when
        List<Schedule> schedules = scheduleRepository.findByUserAndEndDateGreaterThanEqual(user, today);

        // then
        assertThat(schedules.size()).isEqualTo(1);
        assertThat(schedules.get(0).getStartDate()).isEqualTo(startDate);

    }

}