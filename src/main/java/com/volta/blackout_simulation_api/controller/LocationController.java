package com.volta.blackout_simulation_api.controller;

import com.volta.blackout_simulation_api.dto.LocationDto;
import com.volta.blackout_simulation_api.model.Location;
import com.volta.blackout_simulation_api.service.LocationService;
import com.volta.blackout_simulation_api.service.LocationServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;
    private final ModelMapper modelMapper;

    public LocationController(LocationService locationService, ModelMapper modelMapper) {
        this.locationService = locationService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<LocationDto> addLocation(@Valid @RequestBody LocationDto locationDto) {
        Location location = modelMapper.map(locationDto, Location.class);
        LocationDto createdLocation = locationService.createLocation(location).getBody();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLocation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getLocationById(id).getBody());
    }

    @GetMapping
    public ResponseEntity<Page<LocationDto>> getAllLocations(Pageable pageable) {
        return ResponseEntity.ok(locationService.getAllLocations(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable Long id, @Valid @RequestBody LocationDto locationDto) {
        Location location = modelMapper.map(locationDto, Location.class);
        location.setId(id); // Asignamos el ID de la ruta a la entidad
        return ResponseEntity.ok(locationService.updateLocation(location).getBody());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocationById(id);
        return ResponseEntity.noContent().build();
    }
}
