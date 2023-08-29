package com.factory.kafka.config;

import com.factory.kafka.producer.KafkaDataForwarder;
import com.factory.message.FlowRate;
import com.factory.message.GasComposition;
import com.factory.message.NoiseAndVibration;
import com.factory.message.Pressure;
import com.factory.message.Temperature;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

    @Bean
    @ConfigurationProperties("kafka")
    public KafkaConfig kafkaConfig() {
        return new KafkaConfig();
    }

    @Bean
    public ProducerFactory<String, Pressure> pressureProducerFactory() {
        return getKafkaProducerFactory();
    }

    @Bean
    public ProducerFactory<String, Temperature> temperatureProducerFactory() {
        return getKafkaProducerFactory();
    }

    @Bean
    public ProducerFactory<String, GasComposition> gasCompositionProducerFactory() {
        return getKafkaProducerFactory();
    }

    @Bean
    public ProducerFactory<String, NoiseAndVibration> noiseAndVibrationProducerFactory() {
        return getKafkaProducerFactory();
    }


    @Bean
    public ProducerFactory<String, FlowRate> flowRateProducerFactory() {
        return getKafkaProducerFactory();
    }

    @Bean
    public KafkaTemplate<String, Pressure> pressureKafkaTemplate() {
        return new KafkaTemplate<>(pressureProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, Temperature> temperatureKafkaTemplate() {
        return new KafkaTemplate<>(temperatureProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, GasComposition> gasCompositionKafkaTemplate() {
        return new KafkaTemplate<>(gasCompositionProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, NoiseAndVibration> noiseAndVibrationKafkaTemplate() {
        return new KafkaTemplate<>(noiseAndVibrationProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, FlowRate> flowRateKafkaTemplate() {
        return new KafkaTemplate<>(flowRateProducerFactory());
    }

    @Bean
    public KafkaDataForwarder<Pressure> pressureKafkaDataForwarder() {
        return new KafkaDataForwarder<>(pressureKafkaTemplate());
    }

    @Bean
    public KafkaDataForwarder<Temperature> temperatureKafkaDataForwarder() {
        return new KafkaDataForwarder<>(temperatureKafkaTemplate());
    }

    @Bean
    public KafkaDataForwarder<GasComposition> gasCompositionKafkaDataForwarder() {
        return new KafkaDataForwarder<>(gasCompositionKafkaTemplate());
    }

    @Bean
    public KafkaDataForwarder<NoiseAndVibration> noiseAndVibrationKafkaDataForwarder() {
        return new KafkaDataForwarder<>(noiseAndVibrationKafkaTemplate());
    }

    @Bean
    public KafkaDataForwarder<FlowRate> flowRateKafkaDataForwarder() {
        return new KafkaDataForwarder<>(flowRateKafkaTemplate());
    }

    private <T> DefaultKafkaProducerFactory<String, T> getKafkaProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, List.of("localhost:29092"));
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
}
