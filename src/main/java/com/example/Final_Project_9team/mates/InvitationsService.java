package com.example.Final_Project_9team.mates;

import com.example.Final_Project_9team.entity.Mates;
import com.example.Final_Project_9team.entity.Schedule;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.entity.enums.Role;
import com.example.Final_Project_9team.etc.ScheduleRepository;
import com.example.Final_Project_9team.etc.UserRepository;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
//@RequiredArgsConstructor
public class InvitationsService {
    private final MatesRepository matesRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    public InvitationsService(MatesRepository matesRepository, UserRepository userRepository, ScheduleRepository scheduleRepository) {
        this.matesRepository = matesRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
    }

    // 유저가 다른 유저를 초대(test완)
    public ResponseEntity<String> inviteUserToSchedule(String invitingUsername, String invitedUsername, Long scheduleId) {
        // 유저1이 유저2를 초대
        log.info("inviteUserToSchedule메소드 실행");
        User invitedUser = userRepository.findByNickname(invitedUsername).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        log.info("유저 엔티티 찾음");
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                // 추후 스케줄 에러코드 만든 사람의 에러 따서 변경할 예정
                () -> new CustomException(ErrorCode.ERROR_NOT_FOUND));
        log.info("스케줄 엔티티 찾음");
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
    public ResponseEntity<String> acceptInvitation(String nickName,Long scheduleId,Long matesId) {


        User invitedUser = userRepository.findByNickname(nickName).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        // 에러코드 변경예정
        Mates mates = matesRepository.findById(matesId).orElseThrow(
                ()->new CustomException(ErrorCode.ERROR_NOT_FOUND));
        // 에러코드 변경예정
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.ERROR_NOT_FOUND));

        // matesId의 유저정보와 맞는지 확인하기
        if (!mates.getUser().equals(invitedUser)) {
            throw new CustomException(ErrorCode.MATES_NOT_MATCHED_USER);
        }
        //이미 초대를 수락한 경우
        if(mates.getSchedule()!=null)
            throw new CustomException(ErrorCode.ALREADY_ACCEPTED_MATES);

        // 메이트의 초대상태를 수락으로 변경
        mates.setAccepted(true);
        matesRepository.save(mates);

        return ResponseEntity.ok("초대가 정상적으로 되었습니다.");
    }
    // 초대 거절 시(test완)
    public ResponseEntity<String> rejectInvitation(String nickName, Long scheduleId, Long matesId) {
        log.info("rejectInvitation() 실행");
        User invitedUser = userRepository.findByNickname(nickName).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        // 에러코드 변경예정
        Mates mates = matesRepository.findById(matesId).orElseThrow(
                ()->new CustomException(ErrorCode.ERROR_NOT_FOUND));

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                // 추후 스케줄 에러코드 만든 사람의 에러 따서 변경할 예정
                () -> new CustomException(ErrorCode.ERROR_NOT_FOUND));

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
    public ResponseEntity<String> leaveMates(String nickName, Long scheduleId) {
        User invitedUser = userRepository.findByNickname(nickName).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                // 추후 스케줄 에러코드 만든 사람의 에러 따서 변경할 예정
                () -> new CustomException(ErrorCode.ERROR_NOT_FOUND));
        // 유저가 일정에 속해있는지



        return ResponseEntity.ok("메이트에서 탈퇴 되었습니다.");
    }


}
