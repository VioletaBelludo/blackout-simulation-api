package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.LocationDto;
import com.volta.blackout_simulation_api.dto.MinuteDemandDto;
import com.volta.blackout_simulation_api.dto.PowerPlantDto;
import com.volta.blackout_simulation_api.model.Location;
import com.volta.blackout_simulation_api.model.MinuteDemand;
import com.volta.blackout_simulation_api.model.plant.PowerPlant;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PowerPlantService {
    ResponseEntity<PowerPlantDto> createPowerPlant(PowerPlant powerPlant);
    ResponseEntity<PowerPlantDto> updatePowerPlant(PowerPlant powerPlant);
    ResponseEntity<PowerPlantDto> getPowerPlantById(Long id);
    ResponseEntity<List<PowerPlantDto>> getAllPowerPlants();
    ResponseEntity<Void> deletePowerPlantById(Long id);

}
