package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.ResponseDto;
import com.example.Final_Project_9team.service.MatesService;
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
public class MatesController {
    private final MatesService matesService;

    // POST /schedules/{scheduleId}/invited-users
    @PostMapping
    public ResponseEntity<ResponseDto> inviteUserToSchedule(Authentication authentication,
                                                            @PathVariable("scheduleId") Long scheduleId,
                                                            @RequestParam("invitedUsername") String invitedUsername) {
        return matesService.inviteUserToSchedule(authentication.getName(), invitedUsername, scheduleId);
    }

    // POST /schedules/{scheduleId}/acceptance/{matesId}
    @PostMapping("/acceptance/{matesId}")
    public ResponseEntity<ResponseDto> acceptInvitation(Authentication authentication,
                                                   @PathVariable("scheduleId") Long scheduleId,
                                                   @PathVariable Long matesId) {
        return matesService.acceptInvitation(authentication.getName(), scheduleId, matesId);
    }
    // POST /schedules/{scheduleId}/rejection/{matesId}
    @PostMapping("/rejection/{matesId}")
    public ResponseEntity<ResponseDto> rejectInvitation(Authentication authentication,
                                                   @PathVariable("scheduleId") Long scheduleId,
                                                   @PathVariable Long matesId) {
        return matesService.rejectInvitation(authentication.getName(), scheduleId, matesId);
    }
    // POST /schedules/{scheduleId}/drop/{matesId}
    @PostMapping("/drop/{matesId}") // 중간에 일정(메이트) 탈퇴
    public ResponseEntity<ResponseDto> leaveMates(Authentication authentication,
                                                  @PathVariable("scheduleId") Long scheduleId,
                                             @PathVariable Long matesId
    ) {
        return matesService.leaveMates(authentication.getName(), scheduleId,matesId);
    }
}
