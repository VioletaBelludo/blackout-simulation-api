package com.volta.blackout_simulation_api.controller;

import com.volta.blackout_simulation_api.dto.BlackoutSimulationRequestDto;
import com.volta.blackout_simulation_api.dto.SimulationResultDto;
import com.volta.blackout_simulation_api.model.SimulationResult;
import com.volta.blackout_simulation_api.service.BlackoutSimulationService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blackout_simulation")
public class BlackoutSimulationController {
    private final BlackoutSimulationService blackoutSimulationService;
    private final ModelMapper modelMapper;

    public BlackoutSimulationController(BlackoutSimulationService blackoutSimulationService, ModelMapper modelMapper) {
        this.blackoutSimulationService = blackoutSimulationService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<List<SimulationResultDto>> runSimulation(@Valid @RequestBody BlackoutSimulationRequestDto requestDto) {
        List<SimulationResult> results = blackoutSimulationService.runSimulation(requestDto.getBlackoutStart());
        List<SimulationResultDto> resultDtos = results.stream()
                .map(result -> modelMapper.map(result, SimulationResultDto.class))
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(resultDtos);
    }
}
