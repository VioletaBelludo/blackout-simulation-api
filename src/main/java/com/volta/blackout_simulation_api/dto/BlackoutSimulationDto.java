package com.volta.blackout_simulation_api.dto;

import com.volta.blackout_simulation_api.model.MinuteDemand;
import com.volta.blackout_simulation_api.model.plant.PowerPlant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlackoutSimulationDto {
    private List<PowerPlant> allPlants;
    private List<MinuteDemand> minuteDemands;
}
