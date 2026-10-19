package com.example.QuoraApp.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.QuoraApp.configs.KafkaConfig;
import com.example.QuoraApp.events.ViewCountEvent;
import com.example.QuoraApp.repositories.QuestionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class KafkaEventConsumer {
    
    private final QuestionRepository questionRepository;

    @KafkaListener(topics = KafkaConfig.TOPIC_NAME,
                    groupId = "view-count-consumers",
                    containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleViewCountEvent(ViewCountEvent viewCountEvent){

        questionRepository.findById(viewCountEvent.getTargetId())
            .flatMap(question -> {
                Integer views = question.getViews();
                if(views == null){
                    views = 0;
                }
                question.setViews(views +1);
                return questionRepository.save(question);
            })
            .subscribe(updateQuestions -> {
                System.out.println("Question updated Successfully: " + updateQuestions);
            },
            error -> {
                System.out.println("Error updating Question: " + error.getMessage());
            });
    }
}
