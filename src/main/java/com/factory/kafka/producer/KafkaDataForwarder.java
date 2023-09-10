package com.factory.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

@RequiredArgsConstructor
@Slf4j
public class KafkaDataForwarder<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    public void sendMessage(final String topicName, final String key, final T message) {
        var future = kafkaTemplate.executeInTransaction(t -> t.send(topicName, key, message));

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(final Throwable throwable) {
                log.warn("Failed to send message to Kafka, details {}", throwable.getMessage());
            }

            @Override
            public void onSuccess(final SendResult<String, T> sendResult) {
                log.debug("The message has been successfully sent, message {}", sendResult.getProducerRecord().value());
            }
        });
    }
}
