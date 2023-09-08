package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.BoardListResponseDto;
import com.example.Final_Project_9team.dto.ItemListResponseDto;
import com.example.Final_Project_9team.dto.PageDto;
import com.example.Final_Project_9team.dto.ScheduleListResponseDto;
import com.example.Final_Project_9team.entity.Board;
import com.example.Final_Project_9team.entity.LikesBoard;
import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.entity.item.Item;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MyActivityService {
    private final BoardRepository boardRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final LikesBoardRepository likesBoardRepository;


    public PageDto<BoardListResponseDto> readAllBoards(String email, int page, int size) {
        if(!userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        Page<Board> pagedBoards = boardRepository.findAllByUser_EmailAndIsDeletedFalse(
                email,
                PageRequest.of(page - 1, size)
        );

        Page<BoardListResponseDto> pagedDto
                = pagedBoards.map(board -> BoardListResponseDto.fromEntity(
                board,
                likesBoardRepository.countLikesByBoard_Id(board.getId())));

        return PageDto.fromPage(pagedDto);
    }

    @Transactional
    public void likeBoard(String email, Long boardId) {
        if(!userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        if (!boardRepository.existsByIdAndIsDeletedFalse(boardId)) {
                throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
        }

        Optional<LikesBoard> optionalLikes
                = likesBoardRepository.findByUser_EmailAndBoard_Id(email, boardId);

        LikesBoard like = null;
        if (optionalLikes.isPresent()) {
            like = optionalLikes.get();
            like.updateIsLike();
        } else {
            like = LikesBoard.builder()
                    .user(userRepository.findByEmail(email).get())
                    .board(boardRepository.findById(boardId)
                            .orElseThrow(() -> new CustomException(ErrorCode.ERROR_NOT_FOUND)))
                    .isLike(true)
                    .build();
        }
        likesBoardRepository.save(like);
    }

    public PageDto<BoardListResponseDto> readAllLikedBoards(String email, int page, int size) {
        if(!userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        Page<Board> pagedBoards = boardRepository.findAllLikedBoardsByMe(
                email,
                PageRequest.of(page - 1, size)
        );

        Page<BoardListResponseDto> pagedDto
                = pagedBoards.map(board -> BoardListResponseDto.fromEntity(
                board,
                likesBoardRepository.countLikesByBoard_Id(board.getId())));

        return PageDto.fromPage(pagedDto);

    }

    public PageDto<BoardListResponseDto> readAllCommentedBoards(String email, int page, int size) {
        if(!userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        Page<Board> pagedBoards = boardRepository.findAllCommentedBoardsByMe(
                email,
                PageRequest.of(page - 1, size)
        );

        Page<BoardListResponseDto> pagedDto
                = pagedBoards.map(board -> BoardListResponseDto.fromEntity(
                board,
                likesBoardRepository.countLikesByBoard_Id(board.getId())));

        return PageDto.fromPage(pagedDto);
    }

    public PageDto<ScheduleListResponseDto> readAllSchedules(String email, int page, int size) {
        if(!userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        Page<Schedule> pagedSchedules = scheduleRepository.findAllSchedulesContainsMe(
                email,
                PageRequest.of(page - 1, size)
        );

        Page<ScheduleListResponseDto> pagedDto
                = pagedSchedules.map(schedule -> ScheduleListResponseDto.fromEntity(schedule));

        return PageDto.fromPage(pagedDto);
    }

    public PageDto<ScheduleListResponseDto> readAllLikedSchedules(String email, int page, int size) {
        if(!userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        Page<Schedule> pagedSchedules = scheduleRepository.findAllLikedSchedulesByMe(
                email,
                PageRequest.of(page - 1, size)
        );

        Page<ScheduleListResponseDto> pagedDto
                = pagedSchedules.map(schedule -> ScheduleListResponseDto.fromEntity(schedule));

        return PageDto.fromPage(pagedDto);
    }

    public PageDto<ItemListResponseDto> readAllLikedItems(String email, int page, int size) {
        if(!userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        Page<Item> pagedItems = itemRepository.findAllLikedItemsByMe(
                email,
                PageRequest.of(page - 1, size)
        );

        Page<ItemListResponseDto> pagedDto
                = pagedItems.map(item -> ItemListResponseDto.fromEntity(item));

        return PageDto.fromPage(pagedDto);
    }

    // 여행지 상세 페이지에서 여행지를 일정에 추가할 때 보기 위한 일정 목록
    public List<ScheduleListResponseDto> readSchedulesAfterToday(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        LocalDate today = LocalDate.now();
        log.info("일정 조회 기준 - 오늘 날짜 : {}", today);

        List<Schedule> schedules = scheduleRepository.findByUserAndEndDateGreaterThanEqual(user, today);
        List<ScheduleListResponseDto> scheduleListResponses = schedules.stream().map(schedule -> ScheduleListResponseDto.fromEntity(schedule))
                .collect(Collectors.toList());

        return scheduleListResponses;
    }

    // 일정 작성 페이지에 보여질 내가 관심등록한 여행지 리스트 - 내가 가려고 하는 지역의 여행지 목록
    public Page<ItemListResponseDto> readLikedItemsBySido(String email, String sido, int page, int size) {

        Page<Item> pagedItems = itemRepository.findByLikedItemsBySido(email, sido, PageRequest.of(page - 1, size));

        return pagedItems.map(item -> ItemListResponseDto.fromEntity(item));
    }
}
