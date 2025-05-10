package com.factory.mapper.config;

//import com.amazonaws.services.sqs.model.Message;
import com.factory.message.Humidity;
import com.factory.message.HumidityDataRecord;
import com.factory.message.Vibration;
import com.factory.message.Pressure;
import com.factory.message.PressureDataRecord;
import com.factory.message.Temperature;
import com.factory.message.TemperatureDataRecord;
import com.factory.message.VibrationDataRecord;
import com.factory.sqs.model.HumidityDto;
import com.factory.sqs.model.VibrationDto;
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
import software.amazon.awssdk.services.sqs.model.Message;

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
        mapper.createTypeMap(Message.class, HumidityDto.class).setConverter(createDtoConverter(objectMapper, HumidityDto.class));
        mapper.createTypeMap(Message.class, VibrationDto.class).setConverter(createDtoConverter(objectMapper, VibrationDto.class));
        mapper.addConverter(createMessagePressureConverter());
        mapper.addConverter(createMessageTemperatureConverter());
        mapper.addConverter(createMessageHumidityConverter());
        mapper.addConverter(createMessageVibrationConverter());
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
                var messageBody = objectMapper.readTree(source.body());
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

    private static Converter<HumidityDto, Humidity> createMessageHumidityConverter() {
        return new AbstractConverter<>() {
            @Override
            @SneakyThrows
            protected Humidity convert(final HumidityDto dto) {
                if (Objects.isNull(dto)) {
                    return null;
                }
                return Humidity.newBuilder()
                        .setData(HumidityDataRecord.newBuilder()
                                .setHumidity(dto.getHumidity().getHumidity())
                                .build())
                        .setLabel(dto.getLabel())
                        .setTimestamp(dto.getTimestamp())
                        .build();
            }
        };
    }

    private static Converter<VibrationDto, Vibration> createMessageVibrationConverter() {
        return new AbstractConverter<>() {
            @Override
            @SneakyThrows
            protected Vibration convert(final VibrationDto dto) {
                if (Objects.isNull(dto)) {
                    return null;
                }
                return Vibration.newBuilder()
                        .setVibrationData(VibrationDataRecord.newBuilder()
                                .setVibration(dto.getVibrationData().getVibration())
                                .build())
                        .setLabel(dto.getLabel())
                        .setTimestamp(dto.getTimestamp())
                        .build();
            }
        };
    }
}
