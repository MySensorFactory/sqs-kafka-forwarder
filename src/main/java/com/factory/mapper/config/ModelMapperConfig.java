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
        mapper.createTypeMap(Message.class, PressureDto.class).setConverter(createDtoConverter(objectMapper, PressureDto.class));
        mapper.createTypeMap(Message.class, TemperatureDto.class).setConverter(createDtoConverter(objectMapper, TemperatureDto.class));
        mapper.createTypeMap(Message.class, FlowRateDto.class).setConverter(createDtoConverter(objectMapper, FlowRateDto.class));
        mapper.createTypeMap(Message.class, GasCompositionDto.class).setConverter(createDtoConverter(objectMapper, GasCompositionDto.class));
        mapper.createTypeMap(Message.class, NoiseAndVibrationDto.class).setConverter(createDtoConverter(objectMapper, NoiseAndVibrationDto.class));
        mapper.addConverter(createMessagePressureConverter());
        mapper.addConverter(createMessageTemperatureConverter());
        mapper.addConverter(createMessageFlowRateConverter());
        mapper.addConverter(createMessageGasCompositionConverter());
        mapper.addConverter(createMessageNoiseAndVibrationConverter());
        return mapper;
    }

    private static <T> Converter<Message, T> createDtoConverter(final ObjectMapper objectMapper, final Class<T> dtoClass) {
        return new AbstractConverter<>() {
            @Override
            @SneakyThrows
            protected T convert(final Message source) {
                if (Objects.isNull(source)) {
                    return null;
                }
                return getDto(source, dtoClass);
            }

            private T getDto(final Message source, final Class<T> dtoClass) throws JsonProcessingException {
                var messageBody = objectMapper.readTree(source.getBody());
                return objectMapper.readValue(messageBody.get(MESSAGE).asText(), dtoClass);
            }
        };
    }

    private static Converter<PressureDto, Pressure> createMessagePressureConverter() {
        return new AbstractConverter<>() {
            @Override
            @SneakyThrows
            protected Pressure convert(final PressureDto dto) {
                if (Objects.isNull(dto)) {
                    return null;
                }
                return Pressure.newBuilder()
                        .setData(PressureDataRecord.newBuilder()
                                .setPressure(dto.getPressure().getPressure())
                                .build())
                        .setLabel(dto.getLabel())
                        .setTimestamp(dto.getTimestamp())
                        .build();
            }
        };
    }

    private static Converter<TemperatureDto, Temperature> createMessageTemperatureConverter() {
        return new AbstractConverter<>() {
            @Override
            @SneakyThrows
            protected Temperature convert(final TemperatureDto dto) {
                if (Objects.isNull(dto)) {
                    return null;
                }
                return Temperature.newBuilder()
                        .setData(TemperatureDataRecord.newBuilder()
                                .setTemperature(dto.getTemperature().getTemperature())
                                .build())
                        .setLabel(dto.getLabel())
                        .setTimestamp(dto.getTimestamp())
                        .build();
            }
        };
    }

    private static Converter<FlowRateDto, FlowRate> createMessageFlowRateConverter() {
        return new AbstractConverter<>() {
            @Override
            @SneakyThrows
            protected FlowRate convert(final FlowRateDto dto) {
                if (Objects.isNull(dto)) {
                    return null;
                }
                return FlowRate.newBuilder()
                        .setData(FlowRateDataRecord.newBuilder()
                                .setFlowRate((float)dto.getFlowRateData().getFlowRate())
                                .build())
                        .setLabel(dto.getLabel())
                        .setTimestamp(dto.getTimestamp())
                        .build();
            }
        };
    }

    private static Converter<GasCompositionDto, GasComposition> createMessageGasCompositionConverter() {
        return new AbstractConverter<>() {
            @Override
            @SneakyThrows
            protected GasComposition convert(final GasCompositionDto dto) {
                if (Objects.isNull(dto)) {
                    return null;
                }
                return GasComposition.newBuilder()
                        .setData(GasCompositionDataRecord.newBuilder()
                                .setCo2(dto.getCompositionData().getCo2())
                                .setH2(dto.getCompositionData().getH2())
                                .setNh3(dto.getCompositionData().getNh3())
                                .setO2(dto.getCompositionData().getO2())
                                .setN2(dto.getCompositionData().getN2())
                                .build())
                        .setLabel(dto.getLabel())
                        .setTimestamp(dto.getTimestamp())
                        .build();
            }
        };
    }

    private static Converter<NoiseAndVibrationDto, NoiseAndVibration> createMessageNoiseAndVibrationConverter() {
        return new AbstractConverter<>() {
            @Override
            @SneakyThrows
            protected NoiseAndVibration convert(final NoiseAndVibrationDto dto) {
                if (Objects.isNull(dto)) {
                    return null;
                }
                return NoiseAndVibration.newBuilder()
                        .setNoiseData(NoiseDataRecord.newBuilder()
                                .setLevel(dto.getNoiseData().getLevel())
                                .build())
                        .setVibrationData(VibrationDataRecord.newBuilder()
                                .setAmplitude(dto.getVibrationData().getAmplitude())
                                .setFrequency(dto.getVibrationData().getFrequency())
                                .build())
                        .setLabel(dto.getLabel())
                        .setTimestamp(dto.getTimestamp())
                        .build();
            }
        };
    }
}
