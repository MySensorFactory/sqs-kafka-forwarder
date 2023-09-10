package com.factory.kafka.config;

import com.factory.kafka.producer.KafkaDataForwarder;
import com.factory.message.FlowRate;
import com.factory.message.GasComposition;
import com.factory.message.NoiseAndVibration;
import com.factory.message.Pressure;
import com.factory.message.Temperature;
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
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.CLIENT_ID_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

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
    public ProducerFactory<String, GasComposition> gasCompositionProducerFactory(final KafkaConfig kafkaConfig) {
        return getKafkaProducerFactory(kafkaNativeConfig, kafkaConfig.getClientId("gasComposition"));
    }

    @Bean
    public ProducerFactory<String, NoiseAndVibration> noiseAndVibrationProducerFactory(final KafkaConfig kafkaConfig) {
        return getKafkaProducerFactory(kafkaNativeConfig, kafkaConfig.getClientId("noiseAndVibration"));
    }

    @Bean
    public ProducerFactory<String, FlowRate> flowRateProducerFactory(final KafkaConfig kafkaConfig) {
        return getKafkaProducerFactory(kafkaNativeConfig, kafkaConfig.getClientId("flowRate"));
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
    public KafkaTemplate<String, GasComposition> gasCompositionKafkaTemplate(final KafkaConfig kafkaConfig) {
        return new KafkaTemplate<>(gasCompositionProducerFactory(kafkaConfig));
    }

    @Bean
    public KafkaTemplate<String, NoiseAndVibration> noiseAndVibrationKafkaTemplate(final KafkaConfig kafkaConfig) {
        return new KafkaTemplate<>(noiseAndVibrationProducerFactory(kafkaConfig));
    }

    @Bean
    public KafkaTemplate<String, FlowRate> flowRateKafkaTemplate(final KafkaConfig kafkaConfig) {
        return new KafkaTemplate<>(flowRateProducerFactory(kafkaConfig));
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
    public KafkaDataForwarder<GasComposition> gasCompositionKafkaDataForwarder(final KafkaConfig kafkaConfig) {
        return new KafkaDataForwarder<>(gasCompositionKafkaTemplate(kafkaConfig));
    }

    @Bean
    public KafkaDataForwarder<NoiseAndVibration> noiseAndVibrationKafkaDataForwarder(final KafkaConfig kafkaConfig) {
        return new KafkaDataForwarder<>(noiseAndVibrationKafkaTemplate(kafkaConfig));
    }

    @Bean
    public KafkaDataForwarder<FlowRate> flowRateKafkaDataForwarder(final KafkaConfig kafkaConfig) {
        return new KafkaDataForwarder<>(flowRateKafkaTemplate(kafkaConfig));
    }

    private <T> DefaultKafkaProducerFactory<String, T> getKafkaProducerFactory(final KafkaNativeConfig kafkaNativeConfig,
                                                                               final String clientId) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(BOOTSTRAP_SERVERS_CONFIG, kafkaNativeConfig.getBootstrapAddress());
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
