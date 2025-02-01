package com.factory;

import com.factory.kafka.config.KafkaNativeConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(KafkaNativeConfig.class)
public class SqsKafkaForwarder {

    public static void main(String[] args) {
        SpringApplication.run(SqsKafkaForwarder.class, args);
    }

}
