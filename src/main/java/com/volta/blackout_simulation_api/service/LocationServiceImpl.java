package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.LocationDto;
import com.volta.blackout_simulation_api.model.Location;
import com.volta.blackout_simulation_api.repository.LocationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    //Mappers?????????
    private final ModelMapper modelMapper;

    public LocationServiceImpl(LocationRepository locationRepository, ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public LocationDto createLocation(Location location) {
        Location savedLocation = locationRepository.save(location);
        return modelMapper.map(savedLocation, LocationDto.class);
    }

    @Override
    public LocationDto updateLocation(Location location) {
        return null;
    }

    @Override
    public LocationDto saveLocation(Location location) {
        return null;
    }

    @Override
    public LocationDto deleteLocation(Location location) {
        return null;
    }
}