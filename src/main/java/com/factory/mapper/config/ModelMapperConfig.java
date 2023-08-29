package com.factory.mapper.config;

import com.amazonaws.services.sqs.model.Message;
import com.factory.message.FlowRate;
import com.factory.message.FlowRateDataRecord;
import com.factory.message.GasComposition;
import com.factory.message.GasCompositionDataRecord;
import com.factory.message.NoiseAndVibration;
import com.factory.message.NoiseDataRecord;
import com.factory.message.Pressure;
import com.factory.message.PressureDataRecord;
import com.factory.message.Temperature;
import com.factory.message.TemperatureDataRecord;
import com.factory.message.VibrationDataRecord;
import com.factory.sqs.model.FlowRateDto;
import com.factory.sqs.model.GasCompositionDto;
import com.factory.sqs.model.NoiseAndVibrationDto;
import com.factory.sqs.model.PressureDto;
import com.factory.sqs.model.TemperatureDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {

    public static final String MESSAGE = "Message";

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ModelMapper modelMapper(final ObjectMapper objectMapper) {
        var mapper = new ModelMapper();
        mapper.addConverter(createMessagePressureConverter(objectMapper));
        mapper.addConverter(createMessageTemperatureConverter(objectMapper));
        mapper.addConverter(createMessageFlowRateConverter(objectMapper));
        mapper.addConverter(createMessageGasCompositionConverter(objectMapper));
        mapper.addConverter(createMessageNoiseAndVibrationConverter(objectMapper));
        return mapper;
    }

    private static Converter<Message, Pressure> createMessagePressureConverter(final ObjectMapper objectMapper) {
        return new AbstractConverter<>() {
            @Override
            @SneakyThrows
            protected Pressure convert(final Message source) {
                if (Objects.isNull(source)) {
                    return null;
                }
                PressureDto dto = getPressureDto(source);
                return Pressure.newBuilder()
                        .setData(PressureDataRecord.newBuilder()
                                .setPressure(dto.pressure().pressure())
                                .build())
                        .setLabel(dto.label())
                        .setTimestamp(dto.timestamp())
                        .build();
            }

            private PressureDto getPressureDto(final Message source) throws JsonProcessingException {
                var messageBody = objectMapper.readTree(source.getBody());
                return objectMapper.readValue(messageBody.get(MESSAGE).asText(), PressureDto.class);
            }
        };
    }

    private static Converter<Message, Temperature> createMessageTemperatureConverter(final ObjectMapper objectMapper) {
        return new AbstractConverter<>() {
            @Override
            @SneakyThrows
            protected Temperature convert(final Message source) {
                if (Objects.isNull(source)) {
                    return null;
                }
                TemperatureDto dto = getTemperatureDto(source);
                return Temperature.newBuilder()
                        .setData(TemperatureDataRecord.newBuilder()
                                .setTemperature(dto.temperature().temperature())
                                .build())
                        .setLabel(dto.label())
                        .setTimestamp(dto.timestamp())
                        .build();
            }

            private TemperatureDto getTemperatureDto(final Message source) throws JsonProcessingException {
                var messageBody = objectMapper.readTree(source.getBody());
                return objectMapper.readValue(messageBody.get(MESSAGE).asText(), TemperatureDto.class);
            }
        };
    }

    private static Converter<Message, FlowRate> createMessageFlowRateConverter(final ObjectMapper objectMapper) {
        return new AbstractConverter<>() {
            @Override
            @SneakyThrows
            protected FlowRate convert(final Message source) {
                if (Objects.isNull(source)) {
                    return null;
                }
                FlowRateDto dto = getTemperatureDto(source);
                return FlowRate.newBuilder()
                        .setData(FlowRateDataRecord.newBuilder()
                                .setFlowRate(dto.flowRateData().flowRate())
                                .build())
                        .setLabel(dto.label())
                        .setTimestamp(dto.timestamp())
                        .build();
            }

            private FlowRateDto getTemperatureDto(final Message source) throws JsonProcessingException {
                var messageBody = objectMapper.readTree(source.getBody());
                return objectMapper.readValue(messageBody.get(MESSAGE).asText(), FlowRateDto.class);
            }
        };
    }

    private static Converter<Message, GasComposition> createMessageGasCompositionConverter(final ObjectMapper objectMapper) {
        return new AbstractConverter<>() {
            @Override
            @SneakyThrows
            protected GasComposition convert(final Message source) {
                if (Objects.isNull(source)) {
                    return null;
                }
                GasCompositionDto dto = getTemperatureDto(source);
                return GasComposition.newBuilder()
                        .setData(GasCompositionDataRecord.newBuilder()
                                .setCo2(dto.compositionData().co2())
                                .setH2(dto.compositionData().h2())
                                .setNh3(dto.compositionData().nh3())
                                .setO2(dto.compositionData().o2())
                                .setN2(dto.compositionData().n2())
                                .build())
                        .setLabel(dto.label())
                        .setTimestamp(dto.timestamp())
                        .build();
            }

            private GasCompositionDto getTemperatureDto(final Message source) throws JsonProcessingException {
                var messageBody = objectMapper.readTree(source.getBody());
                return objectMapper.readValue(messageBody.get(MESSAGE).asText(), GasCompositionDto.class);
            }
        };
    }

    private static Converter<Message, NoiseAndVibration> createMessageNoiseAndVibrationConverter(final ObjectMapper objectMapper) {
        return new AbstractConverter<>() {
            @Override
            @SneakyThrows
            protected NoiseAndVibration convert(final Message source) {
                if (Objects.isNull(source)) {
                    return null;
                }
                NoiseAndVibrationDto dto = getTemperatureDto(source);
                return NoiseAndVibration.newBuilder()
                        .setNoiseData(NoiseDataRecord.newBuilder()
                                .setLevel(dto.noiseData().level())
                                .build())
                        .setVibrationData(VibrationDataRecord.newBuilder()
                                .setAmplitude(dto.vibrationData().amplitude())
                                .setFrequency(dto.vibrationData().frequency())
                                .build())
                        .setLabel(dto.label())
                        .setTimestamp(dto.timestamp())
                        .build();
            }

            private NoiseAndVibrationDto getTemperatureDto(final Message source) throws JsonProcessingException {
                var messageBody = objectMapper.readTree(source.getBody());
                return objectMapper.readValue(messageBody.get(MESSAGE).asText(), NoiseAndVibrationDto.class);
            }
        };
    }
}
