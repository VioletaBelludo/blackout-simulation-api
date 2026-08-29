package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.LocationDto;
import com.volta.blackout_simulation_api.model.Location;


public interface LocationService {
    LocationDto createLocation(Location location);
    LocationDto updateLocation(Location location);
    LocationDto saveLocation(Location location);
    LocationDto deleteLocation(Location location);
}
