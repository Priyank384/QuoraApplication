package com.example.QuoraApp.producers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.QuoraApp.configs.KafkaConfig;
import com.example.QuoraApp.events.ViewCountEvent;

@Service
public class KafkaEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishViewCountEvent(ViewCountEvent viewCountEvent){
        kafkaTemplate.send(KafkaConfig.TOPIC_NAME, viewCountEvent.getTargetId(), viewCountEvent)
                    .whenComplete((result, err) -> {
                        if(err != null){
                            System.out.println("Error publishing view count event: " + err.getMessage());
                        }
                        else{
                            System.out.println("View count event published successfully: " + result.getRecordMetadata().toString());
                        }
                    });

    }
}
