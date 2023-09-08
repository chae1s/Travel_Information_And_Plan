package com.example.Final_Project_9team.controller;

import com.example.Final_Project_9team.service.AttachmentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class AttachmentsController {
    private final AttachmentsService attachmentService;
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            value = "images"
    )
    public ResponseEntity<String> upload(
            Authentication auth,
            @RequestParam("image") MultipartFile image
    ) {
        return ResponseEntity.ok(
                "/media/" + attachmentService.uploadImage(auth.getName(), image)
        );
    }
}
