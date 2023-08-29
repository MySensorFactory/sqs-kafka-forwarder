package com.factory.sqs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FlowRateDto(String label, long timestamp, FlowRateData flowRateData) {
    @JsonCreator
    public FlowRateDto(
            @JsonProperty("label") final String label,
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("flow_rate") final FlowRateData flowRateData) {
        this.label = label;
        this.timestamp = timestamp;
        this.flowRateData = flowRateData;
    }

    public record FlowRateData(float flowRate) {
        @JsonCreator
        public FlowRateData(@JsonProperty("flow_rate") final float flowRate) {
            this.flowRate = flowRate;
        }
    }
}
