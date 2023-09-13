package com.example.Final_Project_9team.utils;

import com.example.Final_Project_9team.dto.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
// 개발 단계에서 로컬에 파일 저장 삭제하는 구현체
public class LocalFileHandler implements FileHandler{
    public FileDto saveFile(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        String[] fileNameSplit = originalFilename.split("\\.");

        String extension = fileNameSplit[fileNameSplit.length - 1];

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSS");
        Date now = new Date();
        String fileName = formatter.format(now);
        String filenameWithExt = String.format("%s.%s", fileName, extension);
        log.info("파일 이름 : {}", filenameWithExt);

//        String fileDir = System.getProperty("user.dir") + File.separator + "media" + File.separator;
        String fileDir = System.getProperty("user.dir") + File.separator + "src\\main\\resources\\static\\media" + File.separator;
        String filePath = fileDir + filenameWithExt;
        log.info("저장 위치 : {}",filePath);

        try {
            file.transferTo(Path.of(filePath));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        FileDto fileDto = new FileDto();
        fileDto.setExtension(extension);
        fileDto.setSize((float) file.getSize());
        fileDto.setPath(filenameWithExt);

        return fileDto;
    }

    public void deleteFile(String fileFilename) throws FileNotFoundException {
//        String path = System.getProperty("user.dir") + File.separator + "media" + File.separator + fileFilename;

        if (fileFilename == null){
            log.info("filePath == null, 삭제할 파일이 존재하지 않음");
            return;
        }

        String path = System.getProperty("user.dir") + File.separator + "src\\main\\resources\\static\\media" + File.separator + fileFilename;

        File file = ResourceUtils.getFile(path);
        log.info("지울 파일 경로 : {}", file.getPath());
        if (file.exists()) {
            boolean result = file.delete();
            log.info("삭제 성공 여부 : {}", result);
        } else {
            log.info("파일이 이미 삭제되었습니다.");
        }
    }
}
