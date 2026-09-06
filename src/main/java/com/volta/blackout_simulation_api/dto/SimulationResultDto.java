package com.volta.blackout_simulation_api.dto;

import com.volta.blackout_simulation_api.model.plant.PlantType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimulationResultDto {
    private LocalDateTime time;
    private double generatedMW;
    private double expectedDemandMW;
    private double averageStability;
    private Map<PlantType, Double> generatedByTypeMW;
}
