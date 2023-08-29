package com.factory.sqs.listener;

import com.amazonaws.services.sqs.model.Message;
import com.factory.kafka.config.KafkaConfig;
import com.factory.kafka.producer.KafkaDataForwarder;
import com.factory.message.Pressure;
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

        when(kafkaConfig.getPressureTopic()).thenReturn(topicName);

        sqsMessageListener.receivePressureMessage(new Message());

        verify(kafkaDataForwarder, times(1))
                .sendMessage(topicName, Pressure.newBuilder().build());
    }
}







