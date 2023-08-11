package com.factory.sqs.listener;

import com.factory.kafka.config.KafkaConfig;
import com.factory.kafka.producer.KafkaDataForwarder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
class SQSMessageListenerTest {

    @InjectMocks
    private SQSMessageListener sqsMessageListener;

    @Mock
    private KafkaDataForwarder kafkaDataForwarder;

    @Mock
    private KafkaConfig kafkaConfig;

    @Test
    void testReceiveMessage() {
        final String message = "Test message";
        final String senderId = "Sender123";
        final String topicName = "test-topic";

        when(kafkaConfig.getPressureTopicName()).thenReturn(topicName);

        sqsMessageListener.receiveMessage(message, senderId);

        verify(kafkaDataForwarder, times(1))
                .sendMessage(topicName, message);
    }
}







