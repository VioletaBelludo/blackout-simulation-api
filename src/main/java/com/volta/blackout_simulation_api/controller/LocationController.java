package com.volta.blackout_simulation_api.controller;

import com.volta.blackout_simulation_api.dto.LocationDto;
import com.volta.blackout_simulation_api.model.Location;
import com.volta.blackout_simulation_api.service.LocationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationServiceImpl locationService;

    public LocationController(LocationServiceImpl locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    //@Valid?????????????????
    private ResponseEntity<LocationDto> addLocation(@Valid @RequestBody Location location){
        LocationDto createdLocation = location.
        return
    }

    @GetMapping
    private ResponseEntity<LocationDto> getLocation{

    }

    @PutMapping
    private ResponseEntity<LocationDto> updateLocation{

    }

    @DeleteMapping
    private ResponseEntity<LocationDto> deleteLocation{

    }
}
