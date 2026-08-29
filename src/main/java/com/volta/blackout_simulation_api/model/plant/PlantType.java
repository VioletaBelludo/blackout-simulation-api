package com.volta.blackout_simulation_api.model.plant;

import lombok.Getter;

import java.time.Duration;
import java.time.LocalTime;

@Getter
public enum PlantType {
    BIOMASS(false, LocalTime.of(0, 0, 0), LocalTime.of(23, 59, 59), Duration.ofHours(3), 0.5),
    COAL(false, LocalTime.of(0, 0, 0), LocalTime.of(23, 59, 59), Duration.ofHours(8), 0.9),
    COMBINED_CYCLE(false,LocalTime.of(0, 0, 0), LocalTime.of(23, 59, 59), Duration.ofHours(2), 0.7),
    FUEL_GAS(false, LocalTime.of(0,0), LocalTime.of(23,59,59), Duration.ofHours(4), 0.6),
    GEOTHERMAL(true, LocalTime.of(0,0), LocalTime.of(23,59,59), Duration.ofHours(1), 0.7),
    HYDRO(true, LocalTime.of(0,0), LocalTime.of(23,59,59), Duration.ofMinutes(3), 0.8),
    NUCLEAR(false, LocalTime.of(0,0), LocalTime.of(23,59,59), Duration.ofHours(24), 1.0),
    SOLAR(true, LocalTime.of(7,0), LocalTime.of(18,59,59), Duration.ofMinutes(6), 0.1),
    WIND(true, LocalTime.of(0,0), LocalTime.of(23,59,59), Duration.ofMinutes(6), 0.2)
    ;

    private final boolean renewable;
    private final LocalTime availableFromTime;
    private final LocalTime availableToTime;
    private final Duration restartDuration;
    private final double stability;

    PlantType(boolean renewable, LocalTime availableFromTime, LocalTime availableToTime, Duration restartDuration, double stability) {
        this.renewable = renewable;
        this.availableFromTime = availableFromTime;
        this.availableToTime = availableToTime;
        this.restartDuration = restartDuration;
        this.stability = stability;
    }


}
