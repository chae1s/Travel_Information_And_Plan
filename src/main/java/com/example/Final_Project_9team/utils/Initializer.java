package com.example.Final_Project_9team.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
@Slf4j
// 초기에 한번만 실행되어야 되는 메서드 모음
public class Initializer {

    // 파일 저장할 media 경로 만들어 주기
    @PostConstruct
    public void fileDirInit() {
        String fileDir = "media/";
        try {
            Files.createDirectories(Path.of(fileDir));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
