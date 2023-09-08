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
public final class PressureDto {
    private String label;
    private String eventKey;
    private long timestamp;
    private PressureData pressure;

    @JsonCreator
    public PressureDto(
            @JsonProperty("label") final String label,
            @JsonProperty("event_key") final String eventKey,
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("pressure") final PressureData pressure) {
        this.label = label;
        this.eventKey = eventKey;
        this.timestamp = timestamp;
        this.pressure = pressure;
    }

    @NoArgsConstructor
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class PressureData {
        private float pressure;

        @JsonCreator
        public PressureData(@JsonProperty("pressure") final float pressure) {
            this.pressure = pressure;
        }
    }
}
