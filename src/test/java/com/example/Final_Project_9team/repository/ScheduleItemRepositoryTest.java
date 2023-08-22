package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.ScheduleItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.*;;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ScheduleItemRepositoryTest {

    @Autowired
    private ScheduleItemRepository scheduleItemRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    private final String title = "즐거운 여행";
    private final LocalDate startDate = LocalDate.of(2023, 8, 20);
    private final LocalDate endDate = LocalDate.of(2023, 8, 25);
    @Test
    public void scheduleItemRepositoryIsNotNull() {
        assertThat(scheduleItemRepository).isNotNull();
    }

    @Test
    @DisplayName("일별 계획 등록")
    public void createScheduleItem() {
        // given
        Schedule schedule = Schedule.builder()
                .id(1L)
                .title(title)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        schedule = scheduleRepository.save(schedule);
        LocalDate day1 = startDate.plusDays(1L);
        List<ScheduleItem> scheduleItems = new ArrayList<>();
        for (int i = 1; i <= 5 ; i++) {
            ScheduleItem scheduleItem = ScheduleItem.builder()
                    .id((long) i)
                    .schedule(schedule)
                    .tourDate(startDate.plusDays(1L))
                    .build();

            scheduleItems.add(scheduleItem);
        }

        // when
        scheduleItemRepository.saveAll(scheduleItems);

        scheduleItems = scheduleItemRepository.findAllBySchedule(schedule);

        // then
        assertThat(scheduleItems.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("하나의 여행지 일정의 원하는 날짜에 추가")
    public void createDateToScheduleItem() {
        // given
        Schedule schedule = Schedule.builder()
                .id(1L)
                .title(title)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        schedule = scheduleRepository.save(schedule);

        LocalDate tourDate = LocalDate.of(2023, 8, 22);
        ScheduleItem scheduleItem = ScheduleItem.builder()
                .id(1L)
                .schedule(schedule)
                .tourDate(tourDate)
                .build();

        // when
        scheduleItem = scheduleItemRepository.save(scheduleItem);

        // then
        assertThat(scheduleItem.getTourDate()).isEqualTo(tourDate);
    }
}