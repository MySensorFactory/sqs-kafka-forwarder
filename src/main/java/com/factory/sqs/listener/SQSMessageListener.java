package com.factory.sqs.listener;

import com.factory.kafka.config.KafkaConfig;
import com.factory.kafka.producer.KafkaDataForwarder;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
@RequiredArgsConstructor
public class SQSMessageListener {

    private final KafkaDataForwarder kafkaDataForwarder;
    private final KafkaConfig kafkaConfig;

    @SqsListener(value = "${sqs.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(@Payload String message, @Header("SenderId") String senderId) {
        kafkaDataForwarder.sendMessage(kafkaConfig.getPressureTopicName(), message);
    }

}
