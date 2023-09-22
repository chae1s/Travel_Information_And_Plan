package com.example.eodi.service;

import com.example.eodi.dto.FileDto;
import com.example.eodi.entity.Attachments;
import com.example.eodi.entity.User;
import com.example.eodi.exception.CustomException;
import com.example.eodi.exception.ErrorCode;
import com.example.eodi.repository.AttachmentsRepository;
import com.example.eodi.repository.UserRepository;
import com.example.eodi.utils.FileHandler;
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
