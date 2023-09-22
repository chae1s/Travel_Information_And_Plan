package com.example.eodi.repository;

import com.example.eodi.entity.LikesBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikesBoardRepository extends JpaRepository<LikesBoard, Long> {
        //
        Optional<LikesBoard> findByUser_EmailAndBoard_Id(String email, Long boardId);

        // boardId가 일치하는 board에 좋아요 true인 갯수 찾기
        @Query("select count(l) from LikesBoard l where l.board.id = ?1 and l.isLike = true")
        int countLikesByBoard_Id(Long boardId);
}
