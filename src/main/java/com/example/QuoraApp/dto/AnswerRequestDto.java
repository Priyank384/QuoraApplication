package com.example.QuoraApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequestDto {
    
    @NotBlank(message = "Content is Required")
    @Size(min = 10, max = 1000, message = "Content should be between 10 and 1000 characters")
    private String content;

    @NotBlank(message = "QuestionId is required")
    private String questionId;
}
