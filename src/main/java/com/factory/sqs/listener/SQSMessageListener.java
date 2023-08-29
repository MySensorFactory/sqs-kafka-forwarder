package com.factory.sqs.listener;

import com.amazonaws.services.sqs.model.Message;
import com.factory.kafka.config.KafkaConfig;
import com.factory.kafka.producer.KafkaDataForwarder;
import com.factory.message.FlowRate;
import com.factory.message.GasComposition;
import com.factory.message.NoiseAndVibration;
import com.factory.message.Pressure;
import com.factory.message.Temperature;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SQSMessageListener {

    private final KafkaDataForwarder<Pressure> pressureKafkaDataForwarder;
    private final KafkaDataForwarder<Temperature> temperatureKafkaDataForwarder;
    private final KafkaDataForwarder<FlowRate> flowRateKafkaDataForwarder;
    private final KafkaDataForwarder<NoiseAndVibration> noiseAndVibrationKafkaDataForwarder;
    private final KafkaDataForwarder<GasComposition> gasCompositionKafkaDataForwarder;
    private final KafkaConfig kafkaConfig;
    private final ModelMapper modelMapper;

    @SqsListener(value = "${sqs.pressureQueue}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    @SneakyThrows
    public void receivePressureMessage(@Payload Message message) {
        var kafkaMessage = modelMapper.map(message, Pressure.class);
        pressureKafkaDataForwarder.sendMessage(kafkaConfig.getPressureTopic(), kafkaMessage);
    }

    @SqsListener(value = "${sqs.temperatureQueue}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    @SneakyThrows
    public void receiveTemperatureMessage(@Payload Message message) {
        var kafkaMessage = modelMapper.map(message, Temperature.class);
        temperatureKafkaDataForwarder.sendMessage(kafkaConfig.getTemperatureTopic(), kafkaMessage);
    }

    @SqsListener(value = "${sqs.flowRateQueue}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    @SneakyThrows
    public void receiveFlowRateMessage(@Payload Message message) {
        var kafkaMessage = modelMapper.map(message, FlowRate.class);
        flowRateKafkaDataForwarder.sendMessage(kafkaConfig.getFlowRateTopic(), kafkaMessage);
    }

    @SqsListener(value = "${sqs.noiseAndVibrationQueue}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    @SneakyThrows
    public void receiveNoiseAndVibrationMessage(@Payload Message message) {
        var kafkaMessage = modelMapper.map(message, NoiseAndVibration.class);
        noiseAndVibrationKafkaDataForwarder.sendMessage(kafkaConfig.getNoiseAndVibrationTopic(), kafkaMessage);
    }

    @SqsListener(value = "${sqs.gasCompositionQueue}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    @SneakyThrows
    public void receiveGasCompositionMessage(@Payload Message message) {
        var kafkaMessage = modelMapper.map(message, GasComposition.class);
        gasCompositionKafkaDataForwarder.sendMessage(kafkaConfig.getGasCompositionTopic(), kafkaMessage);
    }
}
