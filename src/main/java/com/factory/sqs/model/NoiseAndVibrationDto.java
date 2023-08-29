package com.factory.sqs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record NoiseAndVibrationDto(String label, long timestamp, NoiseData noiseData, VibrationData vibrationData) {
    @JsonCreator
    public NoiseAndVibrationDto(
            @JsonProperty("label") final String label,
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("noise") final NoiseData noiseData,
            @JsonProperty("vibration") final VibrationData vibrationData) {
        this.label = label;
        this.timestamp = timestamp;
        this.noiseData = noiseData;
        this.vibrationData = vibrationData;
    }

    public record NoiseData(float level) {
        @JsonCreator
        public NoiseData(@JsonProperty("level") final float level) {
            this.level = level;
        }
    }

    public record VibrationData(float amplitude, float frequency) {
        @JsonCreator
        public VibrationData(@JsonProperty("amplitude") final float amplitude,
                             @JsonProperty("frequency") final float frequency) {
            this.amplitude = amplitude;
            this.frequency = frequency;
        }
    }
}
