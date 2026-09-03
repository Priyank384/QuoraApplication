package com.example.QuoraApp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuoraApp.dto.QuestionRequestDto;
import com.example.QuoraApp.dto.QuestionResponseDto;
import com.example.QuoraApp.services.QuoraService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/questions")
@AllArgsConstructor
public class QuoraController {
    
    private final QuoraService quoraService;

    @PostMapping()
    public Mono<QuestionResponseDto> createQuestion (@RequestBody QuestionRequestDto questionRequestDto){
        return quoraService.createQuestion(questionRequestDto)
        .doOnSuccess(response -> System.out.println("Question Created Successfully" + response))
        .doOnError(error -> System.out.println("Error Creating Question" + error));
    }

    @GetMapping("/search")
    public Flux<QuestionResponseDto> searchQuestions(@RequestParam String query,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size
    ){
        return quoraService.searchQuestions(query, page, size);
    }
}
