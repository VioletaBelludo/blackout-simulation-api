package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.LocationDto;
import com.volta.blackout_simulation_api.model.Location;
import com.volta.blackout_simulation_api.repository.LocationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;

    public LocationServiceImpl(LocationRepository locationRepository, ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ResponseEntity<LocationDto> createLocation(Location location) {
        Location newLocation = locationRepository.save(location);
        LocationDto locationDto = modelMapper.map(newLocation, LocationDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(locationDto);
    }

    @Override
    public ResponseEntity<LocationDto> updateLocation(Location location) {
        Optional<Location> existingLocation = locationRepository.findById(location.getId());

        if (existingLocation.isPresent()){
            Location locationToUpdate = existingLocation.get();
            locationToUpdate.setId(location.getId());
            locationToUpdate.setCity(location.getCity());
            locationToUpdate.setLatitude(location.getLatitude());
            locationToUpdate.setLongitude(location.getLongitude());
            locationRepository.save(locationToUpdate);
            return ResponseEntity.ok(modelMapper.map(locationToUpdate, LocationDto.class));
        }
        return null;
    }

    @Override
    public ResponseEntity<LocationDto> getLocationById(Long id) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        return locationOptional.map(location -> ResponseEntity.ok(modelMapper.map(location, LocationDto.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        List<Location> allLocations = locationRepository.findAll();
        List<LocationDto> locationDtoList = allLocations.stream().map(location -> modelMapper
                .map(location, LocationDto.class)).toList();
        return ResponseEntity.ok(locationDtoList);
    }

    @Override
    public ResponseEntity<Void> deleteLocationById(Long id) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if(optionalLocation.isPresent()){
            locationRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return null;
    }
}