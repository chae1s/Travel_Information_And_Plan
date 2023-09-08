package com.example.Final_Project_9team.service;

import com.example.Final_Project_9team.dto.FileDto;
import com.example.Final_Project_9team.entity.Attachments;
import com.example.Final_Project_9team.entity.User;
import com.example.Final_Project_9team.exception.CustomException;
import com.example.Final_Project_9team.exception.ErrorCode;
import com.example.Final_Project_9team.repository.AttachmentsRepository;
import com.example.Final_Project_9team.repository.UserRepository;
import com.example.Final_Project_9team.utils.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentsService {
    private final UserRepository userRepository;
    private final AttachmentsRepository attachmentsRepository;
    private final FileHandler fileHandler;

    public String uploadImage(String email, MultipartFile image) {
        User writer = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        FileDto imageDto = fileHandler.saveFile(image);

        attachmentsRepository.save(
                Attachments.builder()
                        .isDeleted(false)
                        .path(imageDto.getPath())
                        .size(imageDto.getSize())
                        .extension(imageDto.getExtension())
                        .build()
        );
        return imageDto.getPath();
    }
}
