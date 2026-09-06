package com.volta.blackout_simulation_api.dto;

import com.volta.blackout_simulation_api.model.Location;
import com.volta.blackout_simulation_api.model.plant.PlantState;
import com.volta.blackout_simulation_api.model.plant.PlantType;
import com.volta.blackout_simulation_api.model.plant.ThermalFuelType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PowerPlantDto {
    private PlantType type;
    private String name;
    private Location location;
    private double maxCapacityMW;
    private LocalDateTime restartInitiationTime;
    private PlantState state;
    private double efficiency;
    private ThermalFuelType thermalFuelType;
}
