package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.LocationDto;
import com.volta.blackout_simulation_api.dto.SimulationResultDto;
import com.volta.blackout_simulation_api.model.Location;
import com.volta.blackout_simulation_api.model.SimulationResult;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SimulationResultService {
    ResponseEntity<SimulationResultDto> createSimulationResult(SimulationResult simulationResult);
    ResponseEntity<SimulationResultDto> updateSimulationResult(SimulationResult simulationResult);
    ResponseEntity<SimulationResultDto> getSimulationResultById(Long id);
    ResponseEntity<List<SimulationResultDto>> getAllSimulationResult();
    ResponseEntity<Void> deleteSimulationResultById(Long id);}
