package com.volta.blackout_simulation_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MinuteDemandDto {
    private LocalTime time;
    private double megawatts;

}
