package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.SimulationResultDto;
import com.volta.blackout_simulation_api.model.SimulationResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SimulationResultService {
    ResponseEntity<SimulationResultDto> createSimulationResult(SimulationResult simulationResult);
    ResponseEntity<SimulationResultDto> updateSimulationResult(SimulationResult simulationResult);
    ResponseEntity<SimulationResultDto> getSimulationResultById(Long id);
    Page<SimulationResultDto> getAllSimulationResult(Pageable pageable);
    ResponseEntity<Void> deleteSimulationResultById(Long id);}
