package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.LocationDto;
import com.volta.blackout_simulation_api.dto.PowerPlantDto;
import com.volta.blackout_simulation_api.model.Location;
import com.volta.blackout_simulation_api.model.plant.PowerPlant;

public interface PowerPlantService {
    PowerPlantDto createPowerPlant(PowerPlant powerPlant);
    PowerPlantDto updatePowerPlant(PowerPlant powerPlant);
    PowerPlantDto savePowerPlant(PowerPlant powerPlant);
    PowerPlantDto deletePowerPlant(PowerPlant powerPlant);

}
