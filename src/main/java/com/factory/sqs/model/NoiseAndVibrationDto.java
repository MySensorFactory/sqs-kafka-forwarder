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
public final class NoiseAndVibrationDto {
    private String label;
    private String eventKey;
    private long timestamp;
    private NoiseData noiseData;
    private VibrationData vibrationData;

    @JsonCreator
    public NoiseAndVibrationDto(
            @JsonProperty("label") final String label,
            @JsonProperty("event_key") final String eventKey,
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("noise") final NoiseData noiseData,
            @JsonProperty("vibration") final VibrationData vibrationData) {
        this.label = label;
        this.eventKey = eventKey;
        this.timestamp = timestamp;
        this.noiseData = noiseData;
        this.vibrationData = vibrationData;
    }

    @NoArgsConstructor
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class NoiseData {
        private float level;

        @JsonCreator
        public NoiseData(@JsonProperty("level") final float level) {
            this.level = level;
        }
    }

    @NoArgsConstructor
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class VibrationData {
        private float amplitude;
        private float frequency;

        @JsonCreator
        public VibrationData(@JsonProperty("amplitude") final float amplitude,
                             @JsonProperty("frequency") final float frequency) {
            this.amplitude = amplitude;
            this.frequency = frequency;
        }
    }
}
