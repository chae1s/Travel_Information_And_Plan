package com.example.Final_Project_9team.utils;

import com.example.Final_Project_9team.dto.FileDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@Component
// 인터페이스로 만들어 DI해서 사용
// 추후에 외부 서버에 파일 저장할 것 고려함
public interface FileHandler {
    FileDto saveFile(MultipartFile file);

    void deleteFile(String filePath) throws FileNotFoundException;

}
