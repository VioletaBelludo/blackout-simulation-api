package com.volta.blackout_simulation_api.controller;

import com.volta.blackout_simulation_api.dto.PowerPlantDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PowerPlantController {
    @PostMapping
    private ResponseEntity<PowerPlantDto> addPowerPlant{

    }

    @GetMapping
    private ResponseEntity<PowerPlantDto> getPowerPlant{
        return ResponseEntity.status(HttpStatus.OK).body();
    }

    @PutMapping
    private ResponseEntity<PowerPlantDto> updatePowerPlant{

    }

    @DeleteMapping
    private ResponseEntity<PowerPlantDto> deletePowerPlant{

    }
}
