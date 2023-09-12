package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByUserAndEndDateGreaterThanEqual(User user, LocalDate today);

    Optional<Schedule> findByIdAndIsDeletedFalse(Long scheduleId);

    // display true인 schedule 페이지 단위 조회
    Page<Schedule> findAllByDisplayTrueAndIsDeletedFalseOrderByIdDesc(Pageable pageable);
    @Query(
            "SELECT s FROM Schedule s " +
                    "INNER JOIN s.mates m " +
                    "INNER JOIN m.user u " +
                    "WHERE " +
                    "u.email =:email " +
                    "AND s.isDeleted = false " +
                    "ORDER BY m.createdAt DESC "
    )
    Page<Schedule> findAllSchedulesContainsMe(@Param("email") String email, Pageable pageable);

    @Query(
            "SELECT s FROM Schedule s " +
                    "INNER JOIN s.likesSchedules ls " +
                    "WHERE " +
                    "ls.user.email =:email " +
                    "AND s.isDeleted = false " +
                    "AND s.display = true " +
                    "ORDER BY ls.id DESC "
    )
    Page<Schedule> findAllLikedSchedulesByMe(@Param("email") String email, Pageable pageable);
    List<Schedule> findAllByUserAndMatesIsAcceptedTrue(User user);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
            "FROM Schedule s " +
            "INNER JOIN s.mates m " +
            "INNER JOIN m.user u " +
            "WHERE u.email = :email " +
            "AND s.id = :scheduleId " +
            "AND s.isDeleted = false " +
            "AND m.isDeleted = false " +
            "AND m.isAccepted = true")
    boolean existsByUserEmailAndScheduleId(@Param("email") String email, @Param("scheduleId")Long scheduleId);

    Page<Schedule> findAllByDisplayAndIsDeletedOrderByIdDesc(boolean isDisplay, boolean isDeleted, Pageable pageable);
}
