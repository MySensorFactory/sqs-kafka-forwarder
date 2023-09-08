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
    private FlowRateData flowRateData;

    @JsonCreator
    public FlowRateDto(
            @JsonProperty("label") final String label,
            @JsonProperty("event_key") final String eventKey,
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("flow_rate") final FlowRateData flowRateData
    ) {
        this.label = label;
        this.timestamp = timestamp;
        this.flowRateData = flowRateData;
        this.eventKey = eventKey;
    }

    @Getter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static final class FlowRateData {
        private float flowRate;

        @JsonCreator
        public FlowRateData(@JsonProperty("flow_rate") final float flowRate) {
            this.flowRate = flowRate;
        }

    }
}
