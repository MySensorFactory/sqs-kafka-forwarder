package com.factory.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties("spring.kafka")
public class KafkaNativeConfig {

    private List<String> bootstrapServers;

    private String schemaRegistryUrl;

    private Boolean autoRegisterSchemas = false;

    private Boolean useSchemasLatestVersion = true;

    private String transactionIdPrefix;
}
