package com.factory.sqs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record GasCompositionDto(String label, long timestamp, GasCompositionData compositionData) {
    @JsonCreator
    public GasCompositionDto(
            @JsonProperty("label") final String label,
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("gas_composition") final GasCompositionData compositionData) {
        this.label = label;
        this.timestamp = timestamp;
        this.compositionData = compositionData;
    }

    public record GasCompositionData(float h2, float n2, float nh3, float o2, float co2) {

        @JsonCreator
        public GasCompositionData(@JsonProperty("h2") final float h2,
                                  @JsonProperty("n2") final float n2,
                                  @JsonProperty("nh3") final float nh3,
                                  @JsonProperty("o2") final float o2,
                                  @JsonProperty("co2") final float co2) {
            this.h2 = h2;
            this.n2 = n2;
            this.nh3 = nh3;
            this.o2 = o2;
            this.co2 = co2;
        }
    }
}
