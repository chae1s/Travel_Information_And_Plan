package com.example.Final_Project_9team.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
@Data
public class PageDto<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;

    public static <T> PageDto<T> fromPage(Page<T> page) {
        PageDto<T> pageDto = new PageDto<>();
        pageDto.setContent(page.getContent());
        pageDto.setPageNumber(page.getNumber());
        pageDto.setPageSize(page.getSize());
        pageDto.setTotalElements(page.getTotalElements());
        return pageDto;
    }
}
