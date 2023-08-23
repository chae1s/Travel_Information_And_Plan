package com.example.Final_Project_9team.mates;

import com.example.Final_Project_9team.entity.Mates;
import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.entity.enums.Role;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.MatesRepository;
import com.example.Final_Project_9team.repository.ScheduleRepository;
import com.example.Final_Project_9team.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvitationsService {
    private final MatesRepository matesRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    // 유저가 다른 유저를 초대(test완)
    public ResponseEntity<String> inviteUserToSchedule(String userEmail, String invitedUsername, Long scheduleId) {
        // 유저1이 유저2를 초대
        log.info("inviteUserToSchedule메소드 실행");
        log.info("invitedUsername="+invitedUsername);
        User invitedUser = userRepository.findByNickname(invitedUsername).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        log.info("유저 엔티티 찾음");
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
        log.info("스케줄 엔티티 찾음");
        // 이미 초대된 경우
        if(matesRepository.existsByScheduleIdAndUserId(scheduleId,invitedUser.getId()))
            throw new CustomException(ErrorCode.ALREADY_INVITED_MATES);

        Mates mate = Mates.builder()
                .user(invitedUser)
                // 초대되기 전이므로 일정은 등록하지 않음->확인필요
                // 초대되지 않아도 일정정보에서 필요할것같아서 추가함.+메이트 테이블만 보면 어떤 일정에서 초대된건지 모르기 때문에
                .schedule(schedule)
                .isAccepted(false) // 초대 수락 여부 초기화
                .isHost(false) // 초대한 사용자가 호스트인지 여부를 초기화
                .build();
        matesRepository.save(mate);
        log.info("inviteUserToSchedule메소드 실행종료");

        return ResponseEntity.ok("\"Invitation sent successfully.");
    }
    // 초대 승낙 시(test완)
    public ResponseEntity<String> acceptInvitation(String userEmail,Long scheduleId,Long matesId) {


        User invitedUser = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Mates mates = matesRepository.findById(matesId).orElseThrow(
                ()->new CustomException(ErrorCode.MATES_NOT_FOUND));
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        // matesId의 유저정보와 맞는지 확인하기
        if (!mates.getUser().equals(invitedUser)) {
            throw new CustomException(ErrorCode.MATES_NOT_MATCHED_USER);
        }
        // 이미 초대를 수락한 경우
        if(mates.getIsAccepted()==true)
            throw new CustomException(ErrorCode.ALREADY_ACCEPTED_MATES);

        // 메이트의 초대상태를 수락으로 변경
        mates.setAccepted(true);
        matesRepository.save(mates);

        return ResponseEntity.ok("초대가 정상적으로 되었습니다.");
    }
    // 초대 거절 시(test완)
    public ResponseEntity<String> rejectInvitation(String userEmail, Long scheduleId, Long matesId) {
        log.info("rejectInvitation() 실행");
        User invitedUser = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        log.info("1번지점");
        Mates mates = matesRepository.findById(matesId).orElseThrow(
                ()->new CustomException(ErrorCode.MATES_NOT_FOUND));
        log.info("2번지점");
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                // 추후 스케줄 에러코드 만든 사람의 에러 따서 변경할 예정
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
        log.info("3번지점");
        // matesId의 유저정보와 맞는지 확인하기
        if (!mates.getUser().equals(invitedUser)) {
            throw new CustomException(ErrorCode.MATES_NOT_MATCHED_USER);
        }
        // 이미 초대를 수락한 경우
        if(mates.getIsAccepted()==true)
            throw new CustomException(ErrorCode.ALREADY_ACCEPTED_MATES);

        matesRepository.delete(mates);

        return ResponseEntity.ok("초대가 거절 되었습니다.");
    }
    // 소속된 일정(메이트)에서 중간에 나가기
    public ResponseEntity<String> leaveMates(String userEmail, Long scheduleId, Long matesId) {
        User invitedUser = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Mates mates = matesRepository.findById(matesId).orElseThrow(
                ()->new CustomException(ErrorCode.MATES_NOT_FOUND));
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        if (!mates.getUser().equals(invitedUser)) // matesId의 유저정보와 맞는지 확인하기
            throw new CustomException(ErrorCode.MATES_NOT_MATCHED_USER);

        if (mates.getIsDeleted() == true) // 이미 탈퇴한 경우
            throw new CustomException(ErrorCode.ALREADY_DELETED_MATES);

        mates.setDeleted(true);
        matesRepository.save(mates);

        return ResponseEntity.ok("해당 메이트를 나갔습니다.");
    }


}
