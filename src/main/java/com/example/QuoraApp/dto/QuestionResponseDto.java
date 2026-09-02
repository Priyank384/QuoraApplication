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
public class QuestionResponseDto {
    private String id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
