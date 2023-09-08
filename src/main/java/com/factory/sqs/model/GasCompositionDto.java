package com.factory.sqs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public final class GasCompositionDto {
    private String label;
    private String eventKey;
    private long timestamp;
    private GasCompositionData compositionData;

    @JsonCreator
    public GasCompositionDto(
            @JsonProperty("label") final String label,
            @JsonProperty("event_key") final String eventKey,
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("gas_composition") final GasCompositionData compositionData) {
        this.label = label;
        this.eventKey = eventKey;
        this.timestamp = timestamp;
        this.compositionData = compositionData;
    }

    @NoArgsConstructor
    @Getter
    @EqualsAndHashCode
    public static final class GasCompositionData {
        private float h2;
        private float n2;
        private float nh3;
        private float o2;
        private float co2;

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
