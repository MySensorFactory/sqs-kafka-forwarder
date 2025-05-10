package com.factory.kafka.config;

import com.factory.kafka.producer.KafkaDataForwarder;
import com.factory.message.Humidity;
import com.factory.message.Pressure;
import com.factory.message.Temperature;
import com.factory.message.Vibration;
import com.factory.message.serialization.AvroSerializer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

import static io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS;
import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final KafkaNativeConfig kafkaNativeConfig;

    @Bean
    @ConfigurationProperties("kafka")
    public KafkaConfig kafkaConfig() {
        return new KafkaConfig();
    }

    @Bean
    public ProducerFactory<String, Pressure> pressureProducerFactory(final KafkaConfig kafkaConfig) {
        return getKafkaProducerFactory(kafkaNativeConfig, kafkaConfig.getClientId("pressure"));
    }

    @Bean
    public ProducerFactory<String, Temperature> temperatureProducerFactory(final KafkaConfig kafkaConfig) {
        return getKafkaProducerFactory(kafkaNativeConfig, kafkaConfig.getClientId("temperature"));
    }

    @Bean
    public ProducerFactory<String, Vibration> vibrationProducerFactory(final KafkaConfig kafkaConfig) {
        return getKafkaProducerFactory(kafkaNativeConfig, kafkaConfig.getClientId("vibration"));
    }

    @Bean
    public ProducerFactory<String, Humidity> humidityProducerFactory(final KafkaConfig kafkaConfig) {
        return getKafkaProducerFactory(kafkaNativeConfig, kafkaConfig.getClientId("humidity"));
    }

    @Bean
    public KafkaTemplate<String, Pressure> pressureKafkaTemplate(final KafkaConfig kafkaConfig) {
        return new KafkaTemplate<>(pressureProducerFactory(kafkaConfig));
    }

    @Bean
    public KafkaTemplate<String, Temperature> temperatureKafkaTemplate(final KafkaConfig kafkaConfig) {
        return new KafkaTemplate<>(temperatureProducerFactory(kafkaConfig));
    }

    @Bean
    public KafkaTemplate<String, Vibration> vibrationKafkaTemplate(final KafkaConfig kafkaConfig) {
        return new KafkaTemplate<>(vibrationProducerFactory(kafkaConfig));
    }

    @Bean
    public KafkaTemplate<String, Humidity> humidityKafkaTemplate(final KafkaConfig kafkaConfig) {
        return new KafkaTemplate<>(humidityProducerFactory(kafkaConfig));
    }

    @Bean
    public KafkaDataForwarder<Pressure> pressureKafkaDataForwarder(final KafkaConfig kafkaConfig) {
        return new KafkaDataForwarder<>(pressureKafkaTemplate(kafkaConfig));
    }

    @Bean
    public KafkaDataForwarder<Temperature> temperatureKafkaDataForwarder(final KafkaConfig kafkaConfig) {
        return new KafkaDataForwarder<>(temperatureKafkaTemplate(kafkaConfig));
    }

    @Bean
    public KafkaDataForwarder<Vibration> vibrationKafkaDataForwarder(final KafkaConfig kafkaConfig) {
        return new KafkaDataForwarder<>(vibrationKafkaTemplate(kafkaConfig));
    }

    @Bean
    public KafkaDataForwarder<Humidity> humidityKafkaDataForwarder(final KafkaConfig kafkaConfig) {
        return new KafkaDataForwarder<>(humidityKafkaTemplate(kafkaConfig));
    }

    private <T> DefaultKafkaProducerFactory<String, T> getKafkaProducerFactory(final KafkaNativeConfig kafkaNativeConfig,
                                                                               final String clientId) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(BOOTSTRAP_SERVERS_CONFIG, kafkaNativeConfig.getBootstrapServers());
        configProps.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);
        configProps.put(CLIENT_ID_CONFIG, clientId);
        configProps.put(SCHEMA_REGISTRY_URL_CONFIG, kafkaNativeConfig.getSchemaRegistryUrl());
        configProps.put(AUTO_REGISTER_SCHEMAS, kafkaNativeConfig.getAutoRegisterSchemas());
        configProps.put(USE_LATEST_VERSION, kafkaNativeConfig.getUseSchemasLatestVersion());
        var result = new DefaultKafkaProducerFactory<String, T>(configProps);
        result.setTransactionIdPrefix(kafkaNativeConfig.getTransactionIdPrefix() + "-" + clientId);
        return result;
    }
}
