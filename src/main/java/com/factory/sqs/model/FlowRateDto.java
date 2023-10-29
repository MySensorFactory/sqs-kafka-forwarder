package com.factory.sqs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public final class FlowRateDto {
    private String label;
    private String eventKey;
    private long timestamp;
    private float flowRate;

    @JsonCreator
    public FlowRateDto(
            @JsonProperty("label") final String label,
            @JsonProperty("event_key") final String eventKey,
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("flow_rate") final float flowRate
    ) {
        this.label = label;
        this.timestamp = timestamp;
        this.flowRate = flowRate;
        this.eventKey = eventKey;
    }
}
