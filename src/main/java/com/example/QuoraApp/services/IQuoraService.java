package com.example.QuoraApp.services;

import org.springframework.stereotype.Service;

import com.example.QuoraApp.dto.QuestionRequestDto;
import com.example.QuoraApp.dto.QuestionResponseDto;

import reactor.core.publisher.Mono;

@Service
public interface IQuoraService {
    
    public Mono<QuestionResponseDto> createQuestion(QuestionRequestDto questionRequestDto);

}
