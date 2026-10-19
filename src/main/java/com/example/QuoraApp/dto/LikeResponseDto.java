package com.example.QuoraApp.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponseDto {
    private String id;

    private String targetId;

    private String targetType;

    private Boolean isLike;

    private LocalDateTime createdAt;
}
