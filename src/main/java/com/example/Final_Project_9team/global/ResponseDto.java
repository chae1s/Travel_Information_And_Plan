package com.example.Final_Project_9team.global;

import lombok.Data;

@Data
public class ResponseDto {
    private String message;

    public static ResponseDto getMessage(String message) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(message);
        return responseDto;
    }
}
