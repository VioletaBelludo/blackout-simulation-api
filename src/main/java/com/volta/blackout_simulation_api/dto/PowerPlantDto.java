package com.volta.blackout_simulation_api.dto;

import com.volta.blackout_simulation_api.model.Location;
import com.volta.blackout_simulation_api.model.plant.PlantState;
import com.volta.blackout_simulation_api.model.plant.PlantType;
import com.volta.blackout_simulation_api.model.plant.ThermalFuelType;

import java.time.LocalDateTime;

public class PowerPlantDto {
    private PlantType type;
    private String name;
    private Location location;
    private double maxCapacityMW;
    private LocalDateTime restartInitiationTime;
    private PlantState state;
    private double efficiency;
    private ThermalFuelType thermalFuelType;

    public PowerPlantDto(PlantType type, String name, Location location, double maxCapacityMW, LocalDateTime restartInitiationTime, PlantState state, double efficiency, ThermalFuelType thermalFuelType) {
        this.type = type;
        this.name = name;
        this.location = location;
        this.maxCapacityMW = maxCapacityMW;
        this.restartInitiationTime = restartInitiationTime;
        this.state = state;
        this.efficiency = efficiency;
        this.thermalFuelType = thermalFuelType;
    }
}
