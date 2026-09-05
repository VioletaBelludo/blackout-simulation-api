package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.PowerPlantDto;
import com.volta.blackout_simulation_api.model.plant.PowerPlant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PowerPlantService {
    ResponseEntity<PowerPlantDto> createPowerPlant(PowerPlant powerPlant);
    ResponseEntity<PowerPlantDto> updatePowerPlant(PowerPlant powerPlant);
    ResponseEntity<PowerPlantDto> getPowerPlantById(Long id);
    Page<PowerPlantDto> getAllPowerPlants(Pageable pageable);
    ResponseEntity<Void> deletePowerPlantById(Long id);

}
