package com.factory.sqs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TemperatureDto(String label, long timestamp, TemperatureData temperature) {
    @JsonCreator
    public TemperatureDto(
            @JsonProperty("label") final String label,
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("temperature") final TemperatureData temperature) {
        this.label = label;
        this.timestamp = timestamp;
        this.temperature = temperature;
    }

    public record TemperatureData(float temperature) {
        @JsonCreator
        public TemperatureData(@JsonProperty("temperature") final float temperature) {
            this.temperature = temperature;
        }
    }
}
