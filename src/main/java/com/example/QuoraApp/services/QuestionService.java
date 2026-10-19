package com.example.QuoraApp.services;

import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.QuoraApp.adapter.QuestionAdapter;
import com.example.QuoraApp.dto.QuestionRequestDto;
import com.example.QuoraApp.dto.QuestionResponseDto;
import com.example.QuoraApp.enums.TargetType;
import com.example.QuoraApp.events.ViewCountEvent;
import com.example.QuoraApp.models.Question;
import com.example.QuoraApp.producers.KafkaEventProducer;
import com.example.QuoraApp.repositories.QuestionRepository;
import com.example.QuoraApp.utils.CursorUtils;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;
    private final KafkaEventProducer kafkaEventProducer;
    
    @Override
    public Mono<QuestionResponseDto> createQuestion(QuestionRequestDto questionRequestDto) {
        Question question = Question.builder()
            .title(questionRequestDto.getTitle())
            .content(questionRequestDto.getContent())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        return questionRepository.save(question)
            .map(QuestionAdapter::toQuestionResponseDto)
            .doOnSuccess(response -> System.out.println("Question Created Successfully" + response))
            .doOnError(error -> System.out.println("Error Creating Question" + error));
    }

    @Override
    public Flux<QuestionResponseDto> searchQuestions(String searchTerm, int offset, int page){
        return questionRepository.findByTitleOrContentContainingIgnoreCase(searchTerm, PageRequest.of(offset, page))
                .map(QuestionAdapter::toQuestionResponseDto)
                .doOnError(error -> System.out.println("Error Searching Questions: " + error))
                .doOnComplete(() -> System.out.println("Questions Searched Successfully"));
                
    }

    @Override
    public Flux<QuestionResponseDto> getAllQuestions(String cursor, int size){
        Pageable pageable = PageRequest.of(0, size);

        if(!CursorUtils.isValidCursor(cursor)){
            return questionRepository.findTop10ByOrderByCreatedAtAsc()
                    .take(size)
                    .map(QuestionAdapter::toQuestionResponseDto)
                    .doOnError(error -> System.out.println("Error Searching Questions: " + error))
                    .doOnComplete(() -> System.out.println("Questions Searched Successfully"));
        }
        else{
            LocalDateTime cursorTimeStamp = CursorUtils.parseCursor(cursor);
            return questionRepository.findByCreatedAtGreaterThanOrderByCreatedAtAsc(cursorTimeStamp, pageable)
                        .map(QuestionAdapter::toQuestionResponseDto)
                        .doOnError(error -> System.out.println("Error Searching Questions: " + error))
                        .doOnComplete(() -> System.out.println("Questions Searched Successfully"));

        }
    }

    @Override
    public Mono<QuestionResponseDto> getQuestionById(String id){

        return questionRepository.findById(id)
            .map(QuestionAdapter::toQuestionResponseDto)
            .doOnError(error -> System.out.println("Error Fetching Questions: " + error))
            .doOnSuccess(response -> {
                System.out.println("Questions Fetched Successfully: " + response);
                ViewCountEvent viewCountEvent = ViewCountEvent.builder()
                                                    .targetId(id)
                                                    .targetType(TargetType.QUESTION)
                                                    .timestamp(LocalDateTime.now())
                                                    .build();
                
                kafkaEventProducer.publishViewCountEvent(viewCountEvent);
            });
    }
}
