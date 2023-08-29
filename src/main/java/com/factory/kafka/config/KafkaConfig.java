package com.factory.kafka.config;

import lombok.Data;

@Data
public class KafkaConfig {
    private String temperatureTopic;
    private String pressureTopic;
    private String noiseAndVibrationTopic;
    private String gasCompositionTopic;
    private String flowRateTopic;
}
