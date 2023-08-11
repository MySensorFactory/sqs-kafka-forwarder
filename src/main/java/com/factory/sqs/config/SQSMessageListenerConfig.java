package com.factory.sqs.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class SQSMessageListenerConfig {

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretKey;

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync(final SQSConfigData sqsConfigData) {
        AWSCredentialsProvider credentials = InstanceProfileCredentialsProvider.getInstance();
        if (Boolean.TRUE.equals(sqsConfigData.getIsLocal())) {
            credentials = new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey));
        }

        return AmazonSQSAsyncClientBuilder.standard()
                .withExecutorFactory(() -> Executors.newFixedThreadPool(20))
                .withRegion(region)
                .withCredentials(credentials)
                .build();
    }

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(final AmazonSQSAsync amazonSQSAsync) {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSQSAsync);
        factory.setMaxNumberOfMessages(10);
        return factory;
    }

    @Bean
    @ConfigurationProperties("sqs")
    public SQSConfigData sqsConfigData(){
        return new SQSConfigData();
    }
}

