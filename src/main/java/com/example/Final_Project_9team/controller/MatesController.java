package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.dto.InvitationResponseDto;
import com.example.Final_Project_9team.dto.ResponseDto;
import com.example.Final_Project_9team.dto.user.UserResponseDto;
import com.example.Final_Project_9team.service.MatesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("schedules/invited-users")
// 일정에 유저 초대하는 기능의 컨틀롤러
public class MatesController {
    private final MatesService matesService;
    // GET /schedules/invited-users
    @GetMapping // 초대 리스트 조회
    public ResponseEntity<List<InvitationResponseDto>> readInvitations(Authentication authentication) {
        return matesService.readInvitations(authentication.getName()); //authentication.getName()v
    }
    // GET
    @GetMapping("/{scheduleId}") // 메이트에 초대할 리스트 검색
    public ResponseEntity<List<UserResponseDto>> findInvitationList(@RequestParam("q") String keyword,
                                                                    @PathVariable("scheduleId") Long scheduleId,
                                                                    Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(matesService.findInvitationList(keyword, auth.getName(),scheduleId));
    }

    // POST /schedules/invited-users/{scheduleId}
    @PostMapping("/{scheduleId}")
    public ResponseEntity<ResponseDto> inviteUserToSchedule(Authentication authentication,
                                                            @PathVariable("scheduleId") Long scheduleId,
                                                            @RequestParam("q") String invitedUsername) {
        return matesService.inviteUserToSchedule(authentication.getName(), invitedUsername, scheduleId);
    }

    // POST /schedules/invited-users/{scheduleId}/acceptance/{matesId}
    @PostMapping("/{scheduleId}/acceptance/{matesId}")
    public ResponseEntity<ResponseDto> acceptInvitation(Authentication authentication,
                                                   @PathVariable("scheduleId") Long scheduleId,
                                                   @PathVariable Long matesId) {
        return matesService.acceptInvitation(authentication.getName(), scheduleId, matesId);//authentication.getName()
    }
    // POST /schedules/invited-users/{scheduleId}/rejection/{matesId}
    @PostMapping("/{scheduleId}/rejection/{matesId}")
    public ResponseEntity<ResponseDto> rejectInvitation(Authentication authentication,
                                                   @PathVariable("scheduleId") Long scheduleId,
                                                   @PathVariable Long matesId) {
        return matesService.rejectInvitation(authentication.getName(), scheduleId, matesId);//authentication.getName()
    }
    // POST /schedules/invited-users/{scheduleId}/drop/{matesId}
    @PostMapping("/{scheduleId}/drop/{matesId}") // 중간에 일정(메이트) 탈퇴
    public ResponseEntity<ResponseDto> leaveMates(Authentication authentication,
                                                  @PathVariable("scheduleId") Long scheduleId,
                                             @PathVariable Long matesId
    ) {
        return matesService.leaveMates(authentication.getName(), scheduleId,matesId);
    }
}
