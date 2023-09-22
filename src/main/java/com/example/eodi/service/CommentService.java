package com.example.eodi.service;

import com.example.eodi.dto.CommentRequestDto;
import com.example.eodi.dto.CommentResponseDto;
import com.example.eodi.entity.Board;
import com.example.eodi.entity.Comment;
import com.example.eodi.entity.User;
import com.example.eodi.exception.CustomException;
import com.example.eodi.exception.ErrorCode;
import com.example.eodi.repository.BoardRepository;
import com.example.eodi.repository.CommentRepository;
import com.example.eodi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentResponseDto create(String email, Long boardId, CommentRequestDto dto) {
        User writer = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Board board =boardRepository.findByIdAndIsDeletedFalse(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));


        Comment newComment = Comment.builder()
                .content(dto.getContent())
                .parentId(dto.getParentId())
                .board(board)
                .user(writer)
                .isDeleted(false)
                .build();

        return CommentResponseDto.fromEntity(commentRepository.save(newComment));
    }

    public CommentResponseDto update(String email, Long boardId, Long commentId, CommentRequestDto dto) {
        User writer = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!boardRepository.existsByIdAndIsDeletedFalse(boardId)) {
            throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
        }

        Comment comment = commentRepository.findByIdAndIsDeletedFalse(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (!writer.equals(comment.getUser())) {
            throw new CustomException(ErrorCode.USER_NO_AUTH);
        }

        comment.updateContent(dto.getContent());
        return CommentResponseDto.fromEntity(commentRepository.save(comment));
    }

    public void delete(String email, Long boardId, Long commentId) {
        User writer = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!boardRepository.existsByIdAndIsDeletedFalse(boardId)) {
            throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
        }

        Comment comment = commentRepository.findByIdAndIsDeletedFalse(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (!writer.equals(comment.getUser())) {
            throw new CustomException(ErrorCode.USER_NO_AUTH);
        }
        comment.delete();
        commentRepository.save(comment);
    }
}
