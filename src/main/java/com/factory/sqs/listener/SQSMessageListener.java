package com.factory.sqs.listener;

import com.factory.kafka.config.KafkaConfig;
import com.factory.kafka.producer.KafkaDataForwarder;
import com.factory.message.Humidity;
import com.factory.message.Pressure;
import com.factory.message.Temperature;
import com.factory.message.Vibration;
import com.factory.sqs.model.HumidityDto;
import com.factory.sqs.model.VibrationDto;
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
    private final KafkaDataForwarder<Humidity> humidityKafkaDataForwarder;
    private final KafkaDataForwarder<Vibration> vibrationKafkaDataForwarder;
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

    @SqsListener(value = "${sqs.humidityQueue}")
    @SneakyThrows
    public void receiveHumidityMessage(@Payload Message message) {
        var dto = modelMapper.map(message, HumidityDto.class);
        var kafkaMessage = modelMapper.map(dto, Humidity.class);
        humidityKafkaDataForwarder.sendMessage(kafkaConfig.getTopicName("humidity"), dto.getEventKey(), kafkaMessage);
    }

    @SqsListener(value = "${sqs.vibrationQueue}")
    @SneakyThrows
    public void receiveVibrationMessage(@Payload Message message) {
        var dto = modelMapper.map(message, VibrationDto.class);
        var kafkaMessage = modelMapper.map(dto, Vibration.class);
        vibrationKafkaDataForwarder.sendMessage(kafkaConfig.getTopicName("vibration"), dto.getEventKey(), kafkaMessage);
    }
}
