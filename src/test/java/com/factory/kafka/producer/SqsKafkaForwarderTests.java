package com.factory.kafka.producer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SqsKafkaForwarderTests {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaDataForwarder kafkaDataForwarder;

    private String topicName;
    private String message;

    @BeforeEach
    void setup() {
        topicName = "test-topic";
        message = "Test message";
    }

    @Test
    void testSendMessageSuccess() {
        ListenableFuture<SendResult<String, String>> listenableFuture = mock(ListenableFuture.class);

        when(kafkaTemplate.send(eq(topicName), anyString(), eq(message)))
                .thenReturn(listenableFuture);

        kafkaDataForwarder.sendMessage(topicName, message);

        verify(listenableFuture, times(1))
                .addCallback(any(ListenableFutureCallback.class));
    }

    @Test
    void testSendMessageFailure() {
        ListenableFuture<SendResult<String, String>> listenableFuture = mock(ListenableFuture.class);

        when(kafkaTemplate.send(eq(topicName), anyString(), eq(message)))
                .thenReturn(listenableFuture);

        // Simulate a failure scenario
        doAnswer(invocation -> {
            ListenableFutureCallback<String> callback = invocation.getArgument(0);
            callback.onFailure(new RuntimeException("Kafka send error"));

            return null;
        }).when(listenableFuture).addCallback(any(ListenableFutureCallback.class));

        kafkaDataForwarder.sendMessage(topicName, message);

        verify(listenableFuture, times(1))
                .addCallback(any(ListenableFutureCallback.class));
    }
}
