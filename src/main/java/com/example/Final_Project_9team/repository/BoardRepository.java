package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // board 1개 조회
    Optional<Board> findByIdAndIsDeletedFalse(Long boardId);

    Boolean existsByIdAndIsDeletedFalse(Long boardId);

    // board 전체 조회
    Page<Board> findAllByIsDeletedFalseOrderByIdDesc(Pageable pageable);

    // user가 작성한 board 전체 조회
    Page<Board> findAllByUser_EmailAndIsDeletedFalse(String email, Pageable pageable);


    // 좋아요한 board 전체 조회
    @Query(
            "SELECT b FROM Board b " +
                    "INNER JOIN b.likesBoards lb " +
                    "WHERE " +
                        "lb.user.email = :email " +
                        "AND lb.isLike = true " +
                        "AND b.isDeleted = false " +
                    "ORDER BY lb.id DESC "
    )
    Page<Board> findAllLikedBoardsByMe(@Param("email") String email, Pageable pageable);

    // 내가 comment 작성한 board 전체 조회
    @Query(
            "SELECT DISTINCT b FROM Board b " +
                    "INNER JOIN b.comments c " +
                    "WHERE " +
                        "c.user.email = :email " +
                        "AND b.isDeleted = false " +
                    "ORDER BY c.createdAt DESC "
    )
    Page<Board> findAllCommentedBoardsByMe(@Param("email") String email, Pageable pageable);

}
