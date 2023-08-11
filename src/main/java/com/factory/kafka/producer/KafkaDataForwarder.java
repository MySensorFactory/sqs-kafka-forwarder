package com.factory.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class KafkaDataForwarder {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(final String topicName, final String message) {
        var future = kafkaTemplate.send(topicName, UUID.randomUUID().toString(), message);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(final Throwable throwable) {
                System.out.println("Failed to send message to Kafka, details: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(final SendResult<String, String> stringStringSendResult) {
                System.out.println("The message has been successfully sent, message: " +
                        stringStringSendResult.getProducerRecord().value());
            }
        });
    }
}
