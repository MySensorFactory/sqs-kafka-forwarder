package com.factory.sqs.config;

import lombok.Data;

@Data
public class SQSConfigData {
    private String queueName;
    private Boolean isLocal;
}
