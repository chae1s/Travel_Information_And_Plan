package com.example.eodi.dto;

import lombok.Data;

@Data
public class FileDto {
    private String path;
    private String extension;
    private Float size;
}
