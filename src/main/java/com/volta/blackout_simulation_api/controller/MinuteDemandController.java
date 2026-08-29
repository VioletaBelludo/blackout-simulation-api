package com.volta.blackout_simulation_api.controller;

import com.volta.blackout_simulation_api.dto.MinuteDemandDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MinuteDemandController {
    @PostMapping
    private ResponseEntity<MinuteDemandDto> addMinuteDemand{

    }

    @GetMapping
    private ResponseEntity<MinuteDemandDto> getMinuteDemand{

    }

    @PutMapping
    private ResponseEntity<MinuteDemandDto> updateMinuteDemand{

    }

    @DeleteMapping
    private ResponseEntity<MinuteDemandDto> deleteMinuteDemand{

    }
}
