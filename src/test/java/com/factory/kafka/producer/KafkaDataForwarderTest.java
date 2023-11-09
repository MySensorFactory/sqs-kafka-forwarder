package com.factory.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class KafkaDataForwarderTest {

    public static final String TEST_MESSAGE = "testMessage";
    public static final String TEST_KEY = "testKey";
    public static final String TEST_TOPIC = "testTopic";
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaDataForwarder<String> kafkaDataForwarder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMessageSuccess() {
        String message = TEST_MESSAGE;

        SendResult<String, String> sendResult = mock(SendResult.class);
        when(sendResult.getProducerRecord()).thenReturn(mock(ProducerRecord.class));
        when(sendResult.getProducerRecord().value()).thenReturn(message);

        ListenableFuture<SendResult<String, String>> future = mock(ListenableFuture.class);
        when(kafkaTemplate.executeInTransaction(any())).thenReturn(future);

        doAnswer(invocation -> {
            ListenableFutureCallback<SendResult> callback = invocation.getArgument(0);
            callback.onSuccess(sendResult);
            return null;
        }).when(future).addCallback(any());

        kafkaDataForwarder.sendMessage(TEST_TOPIC, TEST_KEY, message);

        verify(kafkaTemplate, times(1)).executeInTransaction(any());
        verify(future, times(1)).addCallback(any());
    }

    @Test
    void testSendMessageFailure() {
        ListenableFuture<SendResult<String, String>> future = mock(ListenableFuture.class);
        when(kafkaTemplate.executeInTransaction(any())).thenReturn(future);

        doAnswer(invocation -> {
            ListenableFutureCallback<String> callback = invocation.getArgument(0);
            callback.onFailure(new RuntimeException("Test exception"));
            return null;
        }).when(future).addCallback(any());

        kafkaDataForwarder.sendMessage(TEST_TOPIC, TEST_KEY, TEST_MESSAGE);

        verify(kafkaTemplate, times(1)).executeInTransaction(any());
        verify(future, times(1)).addCallback(any());
    }
}
