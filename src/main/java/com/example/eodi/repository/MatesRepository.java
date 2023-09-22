package com.example.eodi.repository;

import com.example.eodi.entity.Mates;
import com.example.eodi.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatesRepository extends JpaRepository<Mates, Long> {
    List<Mates> findAllBySchedule(Schedule schedule);
    Boolean existsByScheduleIdAndUserId(Long scheduleId, Long userId);

    Optional<Mates> findByScheduleIdAndUserId(Long scheduleId, Long userId);
    List<Mates> findAllByUserIdAndIsDeletedIsFalse(Long userId);

    List<Mates> findAllByUserIdAndIsAcceptedTrue(Long userId);

    List<Mates> findAllByUserIdAndIsAcceptedFalseAndIsDeletedFalse(Long userId);

    List<Mates> findAllByScheduleIdAndIsAcceptedTrue(Long ScheduleId);

    List<Mates> findAllByScheduleIdAndIsAcceptedFalseAndIsDeletedFalse(Long ScheduleId);

    Long countMatesByScheduleAndIsAcceptedTrue(Schedule schedule);

}
