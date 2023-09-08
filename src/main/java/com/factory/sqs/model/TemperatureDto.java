package com.factory.sqs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public final class TemperatureDto {
    private String label;
    private String eventKey;
    private long timestamp;
    private TemperatureData temperature;

    @JsonCreator
    public TemperatureDto(
            @JsonProperty("label") final String label,
            @JsonProperty("event_key") final String eventKey,
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("temperature") final TemperatureData temperature) {
        this.label = label;
        this.eventKey = eventKey;
        this.timestamp = timestamp;
        this.temperature = temperature;
    }

    @NoArgsConstructor
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class TemperatureData {
        private float temperature;

        @JsonCreator
        public TemperatureData(@JsonProperty("temperature") final float temperature) {
            this.temperature = temperature;
        }
    }
}
