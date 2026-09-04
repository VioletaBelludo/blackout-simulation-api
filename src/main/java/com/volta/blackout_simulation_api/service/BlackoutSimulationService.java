package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.BlackoutSimulationDto;
import com.volta.blackout_simulation_api.dto.LocationDto;
import com.volta.blackout_simulation_api.dto.SimulationResultDto;
import com.volta.blackout_simulation_api.model.BlackoutSimulation;
import com.volta.blackout_simulation_api.model.Location;
import com.volta.blackout_simulation_api.model.SimulationResult;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface BlackoutSimulationService {
    List<SimulationResult> runSimulation(LocalDateTime blackoutStart);
}
