package com.factory.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@Slf4j
public class KafkaDataForwarder<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    public void sendMessage(final String topicName, final String key, final T message) {
        var future = kafkaTemplate.executeInTransaction(t -> t.send(topicName, key, message));

        future.whenComplete((result,e) -> {
            if (e != null) {
                log.error("Failed to send message to Kafka, details {}", e.getMessage());
            } else {
                log.debug("Message sent to Kafka successfully, message {}", message);
            }
        });
    }
}
