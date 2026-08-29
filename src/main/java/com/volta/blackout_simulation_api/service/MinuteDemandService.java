package com.volta.blackout_simulation_api.service;


import com.volta.blackout_simulation_api.dto.MinuteDemandDto;
import com.volta.blackout_simulation_api.model.MinuteDemand;

public interface MinuteDemandService {
    MinuteDemandDto createMinuteDemand(MinuteDemand minuteDemand);
    MinuteDemandDto updateMinuteDemand(MinuteDemand minuteDemand);
    MinuteDemandDto saveMinuteDemand(MinuteDemand minuteDemand);
    MinuteDemandDto deleteMinuteDemand(MinuteDemand minuteDemand);
}
