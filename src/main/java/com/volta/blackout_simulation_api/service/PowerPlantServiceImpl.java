package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.LocationDto;
import com.volta.blackout_simulation_api.dto.PowerPlantDto;
import com.volta.blackout_simulation_api.model.Location;
import com.volta.blackout_simulation_api.model.MinuteDemand;
import com.volta.blackout_simulation_api.model.plant.PowerPlant;
import com.volta.blackout_simulation_api.repository.PowerPlantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PowerPlantServiceImpl implements PowerPlantService{

    private final PowerPlantRepository powerPlantRepository;
    private final ModelMapper modelMapper;

    public PowerPlantServiceImpl(PowerPlantRepository powerPlantRepository, ModelMapper modelMapper) {
        this.powerPlantRepository = powerPlantRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ResponseEntity<PowerPlantDto> createPowerPlant(PowerPlant powerPlant) {
        PowerPlant newPowerPlant = powerPlantRepository.save(powerPlant);
        PowerPlantDto powerPlantDto = modelMapper.map(newPowerPlant, PowerPlantDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(powerPlantDto);

    }

    @Override
    public ResponseEntity<PowerPlantDto> updatePowerPlant(PowerPlant powerPlant) {
        Optional<PowerPlant> existingPowerPlant = powerPlantRepository.findById(powerPlant.getId());

        if (existingPowerPlant.isPresent()){
            PowerPlant powerPlantToUpdate = existingPowerPlant.get();
            powerPlantToUpdate.setId(powerPlant.getId());
            powerPlantToUpdate.setLocation(powerPlant.getLocation());
            powerPlantToUpdate.setName(powerPlant.getName());
            powerPlantToUpdate.setType(powerPlant.getType());
            powerPlantToUpdate.setMaxCapacityMW(powerPlant.getMaxCapacityMW());
            powerPlantToUpdate.setRestartInitiationTime(powerPlant.getRestartInitiationTime());
            powerPlantToUpdate.setState(powerPlant.getState());
            powerPlantRepository.save(powerPlantToUpdate);
            return ResponseEntity.ok(modelMapper.map(powerPlantToUpdate, PowerPlantDto.class));
        }
        return null;
    }

    @Override
    public ResponseEntity<PowerPlantDto> getPowerPlantById(Long id) {
        Optional<PowerPlant> powerPlantOptional = powerPlantRepository.findById(id);
        return powerPlantOptional.map(powerPlant -> ResponseEntity.ok(modelMapper.map(powerPlant, PowerPlantDto.class)))
                .orElse(ResponseEntity.notFound().build());

    }

    @Override
    public ResponseEntity<List<PowerPlantDto>> getAllPowerPlants() {
        List<PowerPlant> allPowerPlants = powerPlantRepository.findAll();
        List<PowerPlantDto> powerPlantDtoList = allPowerPlants.stream().map(powerPlant -> modelMapper
                .map(powerPlant, PowerPlantDto.class)).toList();
        return ResponseEntity.ok(powerPlantDtoList);
    }

    @Override
    public ResponseEntity<Void> deletePowerPlantById(Long id) {
        Optional<PowerPlant> optionalPowerPlant = powerPlantRepository.findById(id);
        if(optionalPowerPlant.isPresent()){
            powerPlantRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return null;
    }
}
