package com.example.eodi.utils;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.nio.file.*;


@Component
@Slf4j
public class Initializer {

    @PostConstruct
    public void makeDir() {
        try {
            // 클래스패스의 static 폴더 내에 media 폴더를 만듭니다.
            Path directoryPath = Paths.get("src/main/resources/static/media");
            Files.createDirectories(directoryPath);
            log.info("media 폴더가 생성되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
