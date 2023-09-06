package com.example.Final_Project_9team.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvitationResponseDto {
    private String scheduleHost;
    private String scheduleTitle;
    private Long scheduleId;
    private String invitedTime;
    private Long matesId;

}
