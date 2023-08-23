package com.example.Final_Project_9team.mates;

import com.example.Final_Project_9team.dto.ResponseDto;
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
    public ResponseEntity<ResponseDto> inviteUserToSchedule(
//                       Authentication authentication,
                                                       @PathVariable("scheduleId") Long scheduleId,
                                                       @RequestParam("invitedUsername") String invitedUsername

    ) {
//        return invitationsService.inviteUserToSchedule(authentication.getName(), invitedUsername, scheduleId);
        return invitationsService.inviteUserToSchedule("user123@naver.com", invitedUsername, scheduleId);
    }

    // POST /schedules/{scheduleId}/acceptance/{matesId}
    @PostMapping("/acceptance/{matesId}")
    public ResponseEntity<ResponseDto> acceptInvitation(
                                                   @PathVariable("scheduleId") Long scheduleId,
                                                   @PathVariable Long matesId) {
        return invitationsService.acceptInvitation("u3u3@naver.com", scheduleId, matesId);
    }
    // POST /schedules/{scheduleId}/rejection/{matesId}
    @PostMapping("/rejection/{matesId}")
    public ResponseEntity<ResponseDto> rejectInvitation(
                                                   @PathVariable("scheduleId") Long scheduleId,
                                                   @PathVariable Long matesId) {
        return invitationsService.rejectInvitation("u3u3@naver.com", scheduleId, matesId);
    }
    // POST /schedules/{scheduleId}/drop/{matesId}
    @PostMapping("/drop/{matesId}") // 중간에 일정(메이트) 탈퇴
    public ResponseEntity<ResponseDto> leaveMates(@PathVariable("scheduleId") Long scheduleId,
                                             @PathVariable Long matesId
    ) {
        return invitationsService.leaveMates("u3u3@naver.com", scheduleId,matesId);
    }
}
