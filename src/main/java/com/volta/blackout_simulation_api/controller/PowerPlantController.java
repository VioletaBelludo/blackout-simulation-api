package com.volta.blackout_simulation_api.controller;

import com.volta.blackout_simulation_api.dto.PowerPlantDto;
import com.volta.blackout_simulation_api.model.plant.PowerPlant;
import com.volta.blackout_simulation_api.service.PowerPlantService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/power_plant")
public class PowerPlantController {
    private final PowerPlantService powerPlantService;
    private final ModelMapper modelMapper;

    public PowerPlantController(PowerPlantService powerPlantService, ModelMapper modelMapper) {
        this.powerPlantService = powerPlantService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<PowerPlantDto> addPowerPlant(@Valid @RequestBody PowerPlantDto powerPlantDto) {
        PowerPlant powerPlant = modelMapper.map(powerPlantDto, PowerPlant.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(powerPlantService.createPowerPlant(powerPlant).getBody());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PowerPlantDto> getPowerPlant(@PathVariable Long id) {
        return ResponseEntity.ok(powerPlantService.getPowerPlantById(id).getBody());
    }

    @GetMapping
    public ResponseEntity<Page<PowerPlantDto>> getAllPowerPlants(Pageable pageable) {
        return ResponseEntity.ok(powerPlantService.getAllPowerPlants(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PowerPlantDto> updatePowerPlant(@PathVariable Long id, @Valid @RequestBody PowerPlantDto powerPlantDto) {
        PowerPlant powerPlant = modelMapper.map(powerPlantDto, PowerPlant.class);
        powerPlant.setId(id);
        return ResponseEntity.ok(powerPlantService.updatePowerPlant(powerPlant).getBody());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePowerPlant(@PathVariable Long id) {
        powerPlantService.deletePowerPlantById(id);
        return ResponseEntity.noContent().build();
    }
}
