package com.factory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SqsKafkaForwarder {

    public static void main(String[] args) {
        SpringApplication.run(SqsKafkaForwarder.class, args);
    }

}
