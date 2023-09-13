package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.*;
import com.example.Final_Project_9team.entity.*;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.*;
import com.example.Final_Project_9team.utils.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final LikesBoardRepository likesBoardRepository;

    @Transactional
    public Long create(String email, BoardRequestDto dto) {
        User writer = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return boardRepository.save(
                Board.builder()
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .user(writer)
                        .viewCnt(0)
                        .isDeleted(false)
                        .build()
        ).getId();
    }


    // board page 단위 조회
    public PageDto<BoardListResponseDto> readAll(int page, int size) {
        Page<Board> pagedBoards = boardRepository.findAllByIsDeletedFalseOrderByIdDesc(
                PageRequest.of(page - 1, size)
        );

        Page<BoardListResponseDto> pagedDto
                = pagedBoards.map(board -> BoardListResponseDto.fromEntity(
                board,
                likesBoardRepository.countLikesByBoard_Id(board.getId())));

        return PageDto.fromPage(pagedDto);
    }

    // board 1개 상세조회(댓글 페이지 단위 조회 포함)
    @Transactional
    public BoardResponseDto readOne(Long boardId, int commentPage, int commentSize) {
        Board board = boardRepository.findByIdAndIsDeletedFalse(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        board.updateViewCnt();
        boardRepository.save(board);

        Pageable pageable = PageRequest.of(commentPage - 1, commentSize);
        Page<Comment> comments = commentRepository.findAllByBoard_IdAndIsDeletedFalseOrderByParentIdAndIdAsc(boardId, pageable);
        int likeCnt = likesBoardRepository.countLikesByBoard_Id(boardId);

        return BoardResponseDto.fromEntity(board, comments, likeCnt);
    }

    @Transactional
    // board 수정
    public void update(String email, Long boardId, BoardRequestDto dto) {
        User writer = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Board board = boardRepository.findByIdAndIsDeletedFalse(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        if (!writer.equals(board.getUser())) {
            throw new CustomException(ErrorCode.USER_NO_AUTH);
        }
        // 제목, 내용 수정
        board.updateTitle(dto.getTitle());
        board.updateContent(dto.getContent());

        boardRepository.save(board);
    }

    @Transactional
    public void delete(String email, Long boardId) {
        User writer = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Board board = boardRepository.findByIdAndIsDeletedFalse(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        if (!writer.equals(board.getUser())) {
            throw new CustomException(ErrorCode.USER_NO_AUTH);
        }

        // board에 작성된 comment 전체 논리 삭제
        List<Comment> commentList = board.getComments();
        commentList.forEach(Comment::delete);
        commentRepository.saveAll(commentList);

        // board 논리 삭제
        board.delete();
        boardRepository.save(board);
    }
}
