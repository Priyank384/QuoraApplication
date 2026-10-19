package com.example.QuoraApp.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.QuoraApp.dto.AnswerRequestDto;
import com.example.QuoraApp.dto.AnswerResponseDto;
import com.example.QuoraApp.models.Answer;

import reactor.core.publisher.Mono;

@Repository
public interface AnswerRepository extends ReactiveMongoRepository<Answer, String> {

    // public Mono<AnswerResponseDto> createAnswer(AnswerRequestDto answerRequestDto);

    public Mono<AnswerResponseDto> getAnswerById(String id);
    
} 