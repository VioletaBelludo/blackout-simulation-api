package com.volta.blackout_simulation_api.dto;

import java.time.LocalTime;

public class MinuteDemandDto {
    private LocalTime time;
    private double megawatts;

    public MinuteDemandDto(LocalTime time, double megawatts) {
        this.time = time;
        this.megawatts = megawatts;
    }
}
