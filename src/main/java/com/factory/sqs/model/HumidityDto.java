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
public final class HumidityDto {
    private String label;
    private String eventKey;
    private long timestamp;
    private HumidityData humidity;

    @JsonCreator
    public HumidityDto(
            @JsonProperty("label") final String label,
            @JsonProperty("event_key") final String eventKey,
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("humidity") final HumidityData humidity
    ) {
        this.label = label;
        this.timestamp = timestamp;
        this.humidity = humidity;
        this.eventKey = eventKey;
    }

    @NoArgsConstructor
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class HumidityData {
        private float humidity;

        @JsonCreator
        public HumidityData(@JsonProperty("humidity") final float humidity) {
            this.humidity = humidity;
        }
    }
}
