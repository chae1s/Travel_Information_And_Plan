package com.example.Final_Project_9team.dto;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Getter
@Builder
@RedisHash(value = "redis_schedulePath")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SchedulePathDto {
    @Id
    private String id;
    List<ItemListResponseDto> scheduleItems;
    List<ItemPathDto> itemPaths;
}
