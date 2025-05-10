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
public final class VibrationDto {
    private String label;
    private String eventKey;
    private long timestamp;
    private VibrationData vibrationData;

    @JsonCreator
    public VibrationDto(
            @JsonProperty("label") final String label,
            @JsonProperty("event_key") final String eventKey,
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("vibration") final VibrationData vibrationData) {
        this.label = label;
        this.eventKey = eventKey;
        this.timestamp = timestamp;
        this.vibrationData = vibrationData;
    }

    @NoArgsConstructor
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class VibrationData {
        private float vibration;

        @JsonCreator
        public VibrationData(@JsonProperty("vibration") final float vibration) {
            this.vibration = vibration;
        }
    }
}
