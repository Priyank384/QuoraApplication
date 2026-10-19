package com.example.QuoraApp.services;

import com.example.QuoraApp.dto.LikeRequestDto;
import com.example.QuoraApp.dto.LikeResponseDto;
import com.example.QuoraApp.enums.TargetType;

import reactor.core.publisher.Mono;

public interface ILikeService {

    Mono<LikeResponseDto> createLike(LikeRequestDto likeRequestDto);

    Mono<LikeResponseDto> countLikesByTargetIdAndTargetType(String targetId, TargetType targetType);

    Mono<LikeResponseDto> countDisLikesByTargetIdAndTargetType(String targetId, TargetType targetType);

    Mono<LikeResponseDto> toggleLikes(String targetId, TargetType targetType, Boolean isLike);
    
}