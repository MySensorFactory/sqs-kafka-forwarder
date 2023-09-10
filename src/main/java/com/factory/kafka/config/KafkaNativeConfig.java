package com.factory.kafka.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class KafkaNativeConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.schemaRegistryUrl}")
    private String schemaRegistryUrl;

    @Value(value = "${spring.kafka.autoRegisterSchemas:false}")
    private Boolean autoRegisterSchemas;

    @Value(value = "${spring.kafka.useSchemasLatestVersion:true}")
    private Boolean useSchemasLatestVersion;

    @Value(value = "${spring.kafka.transactionIdPrefix}")
    private String transactionIdPrefix;
}
