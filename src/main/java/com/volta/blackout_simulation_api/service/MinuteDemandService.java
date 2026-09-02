package com.volta.blackout_simulation_api.service;


import com.volta.blackout_simulation_api.dto.LocationDto;
import com.volta.blackout_simulation_api.dto.MinuteDemandDto;
import com.volta.blackout_simulation_api.model.Location;
import com.volta.blackout_simulation_api.model.MinuteDemand;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MinuteDemandService {
    ResponseEntity<MinuteDemandDto> createMinuteDemand(MinuteDemand minuteDemand);
    ResponseEntity<MinuteDemandDto> updateMinuteDemand(MinuteDemand minuteDemand);
    ResponseEntity<MinuteDemandDto> getMinuteDemandById(Long id);
    ResponseEntity<List<MinuteDemandDto>> getAllMinuteDemands();
    ResponseEntity<Void> deleteMinuteDemandById(Long id);
}
