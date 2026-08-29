package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.BlackoutSimulationDto;
import com.volta.blackout_simulation_api.dto.LocationDto;
import com.volta.blackout_simulation_api.model.BlackoutSimulation;
import com.volta.blackout_simulation_api.model.Location;

public interface BlackoutSimulationService {
    BlackoutSimulationDto createBlackoutSimulation(BlackoutSimulation blackoutSimulation);
    BlackoutSimulationDto updateBlackoutSimulation(BlackoutSimulation blackoutSimulation);
    BlackoutSimulationDto saveBlackoutSimulation(BlackoutSimulation blackoutSimulation);
    BlackoutSimulationDto deleteBlackoutSimulation(BlackoutSimulation blackoutSimulation);
}
