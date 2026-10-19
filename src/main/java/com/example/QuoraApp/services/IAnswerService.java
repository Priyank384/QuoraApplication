package com.example.QuoraApp.services;

import com.example.QuoraApp.dto.LikeRequestDto;
import com.example.QuoraApp.dto.LikeResponseDto;

import reactor.core.publisher.Mono;

public interface IAnswerService {

    Mono<LikeResponseDto> createLike(LikeRequestDto likeRequestDto);
    Mono<LikeResponseDto> countLikesByTargetIdAndTargetType(String targetId, String targetType);
    Mono<LikeResponseDto> countDisLikesByTargetIdAndTargetType(String targetId, String targetType);
    Mono<LikeResponseDto> toggleLike(String targetId, String targetType, Boolean isLike);
}