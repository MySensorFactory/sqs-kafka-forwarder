package com.factory.sqs.listener;

import com.factory.kafka.config.KafkaConfig;
import com.factory.kafka.producer.KafkaDataForwarder;
import com.factory.message.FlowRate;
import com.factory.message.GasComposition;
import com.factory.message.NoiseAndVibration;
import com.factory.message.Pressure;
import com.factory.message.Temperature;
import com.factory.sqs.model.FlowRateDto;
import com.factory.sqs.model.GasCompositionDto;
import com.factory.sqs.model.NoiseAndVibrationDto;
import com.factory.sqs.model.PressureDto;
import com.factory.sqs.model.TemperatureDto;
import io.awspring.cloud.sqs.annotation.SqsListener ;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;
import software.amazon.awssdk.services.sqs.model.Message;

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

    @SqsListener(value = "${sqs.pressureQueue}")
    @SneakyThrows
    public void receivePressureMessage(Message message) {
        var dto = modelMapper.map(message, PressureDto.class);
        var kafkaMessage = modelMapper.map(dto, Pressure.class);
        pressureKafkaDataForwarder.sendMessage(kafkaConfig.getTopicName("pressure"), dto.getEventKey(), kafkaMessage);
    }

    @SqsListener(value = "${sqs.temperatureQueue}")
    @SneakyThrows
    public void receiveTemperatureMessage(@Payload Message message) {
        var dto = modelMapper.map(message, TemperatureDto.class);
        var kafkaMessage = modelMapper.map(dto, Temperature.class);
        temperatureKafkaDataForwarder.sendMessage(kafkaConfig.getTopicName("temperature"), dto.getEventKey(), kafkaMessage);
    }

    @SqsListener(value = "${sqs.flowRateQueue}")
    @SneakyThrows
    public void receiveFlowRateMessage(@Payload Message message) {
        var dto = modelMapper.map(message, FlowRateDto.class);
        var kafkaMessage = modelMapper.map(dto, FlowRate.class);
        flowRateKafkaDataForwarder.sendMessage(kafkaConfig.getTopicName("flowRate"), dto.getEventKey(), kafkaMessage);
    }

    @SqsListener(value = "${sqs.noiseAndVibrationQueue}")
    @SneakyThrows
    public void receiveNoiseAndVibrationMessage(@Payload Message message) {
        var dto = modelMapper.map(message, NoiseAndVibrationDto.class);
        var kafkaMessage = modelMapper.map(dto, NoiseAndVibration.class);
        noiseAndVibrationKafkaDataForwarder.sendMessage(kafkaConfig.getTopicName("noiseAndVibration"), dto.getEventKey(), kafkaMessage);
    }

    @SqsListener(value = "${sqs.gasCompositionQueue}")
    @SneakyThrows
    public void receiveGasCompositionMessage(@Payload Message message) {
        var dto = modelMapper.map(message, GasCompositionDto.class);
        var kafkaMessage = modelMapper.map(dto, GasComposition.class);
        gasCompositionKafkaDataForwarder.sendMessage(kafkaConfig.getTopicName("gasComposition"), dto.getEventKey(), kafkaMessage);
    }
}
