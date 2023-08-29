package com.factory.sqs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PressureDto(String label, long timestamp, PressureData pressure) {
    @JsonCreator
    public PressureDto(
            @JsonProperty("label") final String label,
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("pressure") final PressureData pressure) {
        this.label = label;
        this.timestamp = timestamp;
        this.pressure = pressure;
    }

    public record PressureData(float pressure) {
        @JsonCreator
        public PressureData(@JsonProperty("pressure") final float pressure) {
            this.pressure = pressure;
        }
    }
}
