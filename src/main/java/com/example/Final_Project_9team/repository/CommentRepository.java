package com.example.Final_Project_9team.repository;

import com.example.Final_Project_9team.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // commentId로 삭제되지 않은 comment 찾기
    Optional<Comment> findByIdAndIsDeletedFalse(Long commentId);

    // boardId로 삭제되지 않은 comment 전체 조회해서 댓글/대댓글의 순서로 정렬해서 반환
    // parentId가 null인 경우에는 id를 기준으로 오름차순으로 정렬
    // parentId가 !null이 경우에는 id == parentid인 comment 다음에 id를 오름차순 정렬
    @Query("SELECT c FROM Comment c " +
            "WHERE c.board.id = :boardId AND c.isDeleted = false " +
            "ORDER BY " +
            "CASE WHEN c.parentId IS NULL THEN c.id ELSE c.parentId END " +
            "ASC, c.id ASC")
    Page<Comment> findAllByBoard_IdAndIsDeletedFalseOrderByParentIdAndIdAsc(@Param("boardId") Long boardId, Pageable pageable);

    // userId로 삭제되지 않은 commnet를 commentId 내림차순으로 전체 조회
    Page<Comment> findAllByUser_IdAndIsDeletedFalseOrderByIdDesc(Long userId, Pageable pageable);
}
