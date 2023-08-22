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
                                                       @PathVariable("scheduleId") Long scheduleId,
                                                       @RequestParam("invitedUsername") String invitedUsername

    ) {
//        return invitationsService.inviteUserToSchedule(authentication.getName(), invitedUsername, scheduleId);
        log.info("controller 실행");
        return invitationsService.inviteUserToSchedule("user1", invitedUsername, scheduleId);
    }

    // POST /schedules/{scheduleId}/acceptance/{matesId}
    @PostMapping("/invited-users/acceptance/{matesId}")
    public ResponseEntity<String> acceptInvitation(
//            Authentication authentication,
                                                   @PathVariable("scheduleId") Long scheduleId,
                                                   @PathVariable Long matesId) {
        return invitationsService.acceptInvitation("user2", scheduleId, matesId);
    }
    // POST /schedules/{scheduleId}/rejection/{matesId}
    @PostMapping("/invited-users/rejection/{matesId}")
    public ResponseEntity<String> rejectInvitation(
//            Authentication authentication,
                                                   @PathVariable("scheduleId") Long scheduleId,
                                                   @PathVariable Long matesId) {
        return invitationsService.rejectInvitation("user3", scheduleId, matesId);
    }
    // TODO 초대된 유저가 중간에 일정(메이트)에서 나가기
    // TODO 고민 위 메소드들에서 url에서 mateId를 뺴야할지
    // 우선 메이트 탈퇴는 일정,유저정보만 가지고 하기
    @PutMapping
    public ResponseEntity<String> leaveMates(@PathVariable("scheduleId") Long scheduleId
//    Authentication authentication
    ) {
        return invitationsService.leaveMates("user2", scheduleId);
    }
}
