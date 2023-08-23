package com.example.Final_Project_9team.mates;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("schedules/{scheduleId}/invited-users")
// 일정에 유저 초대하는 기능의 컨틀롤러
public class InvitationController {
    private final InvitationsService invitationsService;

    // POST /schedules/{scheduleId}/invited-users
    @PostMapping
    public ResponseEntity<String> inviteUserToSchedule(
//                       Authentication authentication,
                                                       @PathVariable("scheduleId") Long scheduleId,
                                                       @RequestParam("invitedUsername") String invitedUsername

    ) {
//        return invitationsService.inviteUserToSchedule(authentication.getName(), invitedUsername, scheduleId);
        log.info("controller 실행");
        return invitationsService.inviteUserToSchedule("user123@naver.com", invitedUsername, scheduleId);
    }

    // POST /schedules/{scheduleId}/acceptance/{matesId}        u3u3@naver.com
    @PostMapping("/acceptance/{matesId}")
    public ResponseEntity<String> acceptInvitation(
//            Authentication authentication,
                                                   @PathVariable("scheduleId") Long scheduleId,
                                                   @PathVariable Long matesId) {
        return invitationsService.acceptInvitation("dani123@naver.com", scheduleId, matesId);
    }
    // POST /schedules/{scheduleId}/rejection/{matesId}
    @PostMapping("/rejection/{matesId}")
    public ResponseEntity<String> rejectInvitation(
//            Authentication authentication,
                                                   @PathVariable("scheduleId") Long scheduleId,
                                                   @PathVariable Long matesId) {
        return invitationsService.rejectInvitation("u3u3@naver.com", scheduleId, matesId);
    }
    // TODO 초대된 유저가 중간에 일정(메이트)에서 나가기
    // 우선 메이트 탈퇴는 일정,유저정보만 가지고 하기
    @PutMapping
    public ResponseEntity<String> leaveMates(@PathVariable("scheduleId") Long scheduleId,
                                             @PathVariable Long matesId
//    Authentication authentication
    ) {
        return invitationsService.leaveMates("dani123@naver.com", scheduleId,matesId);
    }
}
