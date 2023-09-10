package com.factory.kafka.config;

import lombok.Data;

import java.util.Map;

@Data
public class KafkaConfig {
    private Map<String, SensorConfig> sensorConfig;

    public String getTopicName(final String sensorName){
        return sensorConfig.get(sensorName).getTopicName();
    }

    public String getClientId(final String sensorName){
        return sensorConfig.get(sensorName).getClientId();
    }
}
