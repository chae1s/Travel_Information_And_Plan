package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.ResponseDto;
import com.example.Final_Project_9team.entity.Mates;
import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.MatesRepository;
import com.example.Final_Project_9team.repository.ScheduleRepository;
import com.example.Final_Project_9team.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatesService {
    private final MatesRepository matesRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    // 유저가 다른 유저를 초대
    public ResponseEntity<ResponseDto> inviteUserToSchedule(String userEmail, String invitedUsername, Long scheduleId) {
        User invitedUser = userRepository.findByNickname(invitedUsername).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        Optional<Mates> optionalMates = matesRepository.findByScheduleIdAndUserId(scheduleId, invitedUser.getId());
        // 이미 메이트가 존재하는 경우(이전에 초대된 적이 있다.)
        if (optionalMates.isPresent()) {
            Mates mates = optionalMates.get();
            if (mates.getIsDeleted() == true) { // 1. 탈퇴했던 경우
                mates.setDeleted(false);
                matesRepository.save(mates);
            } else { // 2. 이미 초대한 경우
                throw new CustomException(ErrorCode.ALREADY_INVITED_MATES);
            }
        } else {
            Mates mate = Mates.builder()
                    .user(invitedUser)
                    .schedule(schedule)
                    .isDeleted(false)
                    .isAccepted(false)
                    .isHost(false)
                    .build();
            matesRepository.save(mate);
        }

        return ResponseEntity.ok(ResponseDto.getMessage("초대가 정상적으로 되었습니다."));
    }
    // 초대 승낙 시
    public ResponseEntity<ResponseDto> acceptInvitation(String userEmail,Long scheduleId,Long matesId) {

        User invitedUser = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Mates mates = matesRepository.findById(matesId).orElseThrow(
                ()->new CustomException(ErrorCode.MATES_NOT_FOUND));
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        // matesId의 유저정보와 맞는지 확인하기
        isMatchedUserAndMates(invitedUser, mates);

        // 이미 초대를 수락한 경우
        isAcceptedMates(mates);
        // 이미 탈퇴한 경우
        isLeftMates(mates);

        mates.setAccepted(true);
        matesRepository.save(mates);

        return ResponseEntity.ok(ResponseDto.getMessage("초대가 정상적으로 수락되었습니다."));
    }

    // 초대 거절 시
    public ResponseEntity<ResponseDto> rejectInvitation(String userEmail, Long scheduleId, Long matesId) {
        log.info("rejectInvitation() 실행");
        User invitedUser = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Mates mates = matesRepository.findById(matesId).orElseThrow(
                ()->new CustomException(ErrorCode.MATES_NOT_FOUND));
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        isMatchedUserAndMates(invitedUser, mates);
        isAcceptedMates(mates);
        isLeftMates(mates);

        matesRepository.delete(mates);
        return ResponseEntity.ok(ResponseDto.getMessage("초대가 정상적으로 거절되었습니다."));

    }
    // 소속된 일정(메이트)에서 나가기(탈퇴)
    public ResponseEntity<ResponseDto> leaveMates(String userEmail, Long scheduleId, Long matesId) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Mates mates = matesRepository.findById(matesId).orElseThrow(
                ()->new CustomException(ErrorCode.MATES_NOT_FOUND));
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        isMatchedUserAndMates(user, mates);
        isLeftMates(mates);

        mates.setDeleted(true);
        mates.setAccepted(false);
        matesRepository.save(mates);

        return ResponseEntity.ok(ResponseDto.getMessage("해당 일정에서 나갔습니다."));
    }
    public void isMatchedUserAndMates(User invitedUser, Mates mates) {
        if (!mates.getUser().equals(invitedUser))
            throw new CustomException(ErrorCode.MATES_NOT_MATCHED_USER);
    }
    public void isAcceptedMates(Mates mates) {
        if(mates.getIsAccepted()==true)
            throw new CustomException(ErrorCode.ALREADY_ACCEPTED_MATES);
    }
    public void isLeftMates(Mates mates) {
        log.info(mates.getIsDeleted().toString());
        if (mates.getIsDeleted() == true)
            throw new CustomException(ErrorCode.ALREADY_LEFT_MATES);
    }
}
