package com.factory.kafka.config;

import lombok.Data;

@Data
public class SensorConfig {
    private String topicName;
    private String clientId;
}
