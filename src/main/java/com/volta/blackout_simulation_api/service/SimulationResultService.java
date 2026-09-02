package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.LocationDto;
import com.volta.blackout_simulation_api.dto.SimulationResultDto;
import com.volta.blackout_simulation_api.model.Location;
import com.volta.blackout_simulation_api.model.SimulationResult;

public interface SimulationResultService {
    SimulationResultDto createSimulationResult(SimulationResult simulationResult);
    SimulationResultDto readSimulationResult(SimulationResult simulationResult);
    SimulationResultDto updateSimulationResult(SimulationResult simulationResult);
    SimulationResultDto deleteSimulationResult(SimulationResult simulationResult);
}
