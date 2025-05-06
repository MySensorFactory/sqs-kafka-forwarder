package com.factory.sqs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public final class HumidityDto {
    private String label;
    private String eventKey;
    private long timestamp;
    private float value;

    @JsonCreator
    public HumidityDto(
            @JsonProperty("label") final String label,
            @JsonProperty("event_key") final String eventKey,
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("value") final float value
    ) {
        this.label = label;
        this.timestamp = timestamp;
        this.value = value;
        this.eventKey = eventKey;
    }
}
