package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.LocationDto;
import com.volta.blackout_simulation_api.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


public interface LocationService {
    ResponseEntity<LocationDto> createLocation(Location location);
    ResponseEntity<LocationDto> updateLocation(Location location);
    ResponseEntity<LocationDto> getLocationById(Long id);
    Page<LocationDto> getAllLocations(Pageable pageable);
    ResponseEntity<Void> deleteLocationById(Long id);
}
