package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.LocationDto;
import com.volta.blackout_simulation_api.model.Location;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface LocationService {
    ResponseEntity<LocationDto> createLocation(Location location);
    ResponseEntity<LocationDto> updateLocation(Location location);
    ResponseEntity<LocationDto> getLocationById(Long id);
    ResponseEntity<List<LocationDto>> getAllLocations();
    ResponseEntity<Void> deleteLocationById(Long id);
}
