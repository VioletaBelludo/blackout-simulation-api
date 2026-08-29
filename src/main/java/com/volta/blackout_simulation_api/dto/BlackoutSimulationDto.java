package com.volta.blackout_simulation_api.dto;

import com.volta.blackout_simulation_api.model.MinuteDemand;
import com.volta.blackout_simulation_api.model.plant.PowerPlant;

import java.util.List;

public class BlackoutSimulationDto {
    private List<PowerPlant> allPlants;
    private MinuteDemand[] minuteDemands;

    public BlackoutSimulationDto(Long id, List<PowerPlant> allPlants, MinuteDemand[] minuteDemands) {
        this.allPlants = allPlants;
        this.minuteDemands = minuteDemands;
    }
}
