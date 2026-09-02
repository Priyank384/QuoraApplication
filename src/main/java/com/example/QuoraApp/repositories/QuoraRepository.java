package com.example.QuoraApp.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.QuoraApp.models.Question;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface QuoraRepository extends ReactiveMongoRepository<Question, String> {

    // Flux<Question> findByAuthorId(String authorId);    

    // Mono<Long> countByAuthorId(String authorId);
}
