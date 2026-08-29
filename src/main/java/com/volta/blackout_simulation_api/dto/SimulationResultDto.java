package com.volta.blackout_simulation_api.dto;

import com.volta.blackout_simulation_api.model.plant.PlantType;

import java.time.LocalDateTime;
import java.util.Map;

public class SimulationResultDto {
    private LocalDateTime time;
    private double generatedMW;
    private double expectedDemandMW;
    private double averageStability;
    private Map<PlantType, Double> generatedByTypeMW;

    public SimulationResultDto(LocalDateTime time, double generatedMW, double expectedDemandMW, double averageStability, Map<PlantType, Double> generatedByTypeMW) {
        this.time = time;
        this.generatedMW = generatedMW;
        this.expectedDemandMW = expectedDemandMW;
        this.averageStability = averageStability;
        this.generatedByTypeMW = generatedByTypeMW;
    }
}
