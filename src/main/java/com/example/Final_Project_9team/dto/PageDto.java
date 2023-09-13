package com.example.Final_Project_9team.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
@Data
public class PageDto<T> {
    private List<T> content;
    private int pageNumber;
    private int lastPage;

    public static <T> PageDto<T> fromPage(Page<T> page) {
        PageDto<T> pageDto = new PageDto<>();
        pageDto.setContent(page.getContent());
        pageDto.setPageNumber(page.getNumber() + 1); // 페이지 번호 1부터 시작
        pageDto.setLastPage(page.getTotalPages()); // 마지막 페이지 번호 = 총 페이지 수
        return pageDto;
    }
}
