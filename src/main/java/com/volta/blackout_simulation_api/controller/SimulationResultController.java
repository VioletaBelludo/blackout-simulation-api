package com.volta.blackout_simulation_api.controller;

import com.volta.blackout_simulation_api.dto.SimulationResultDto;
import com.volta.blackout_simulation_api.model.SimulationResult;
import com.volta.blackout_simulation_api.service.SimulationResultService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/simulations_result")
public class SimulationResultController {

    private final SimulationResultService simulationResultService;
    private final ModelMapper modelMapper;

    public SimulationResultController(SimulationResultService simulationResultService, ModelMapper modelMapper) {
        this.simulationResultService = simulationResultService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<SimulationResultDto> addSimulationResult(@Valid @RequestBody SimulationResultDto simulationResultDto) {
        SimulationResult simulationResult = modelMapper.map(simulationResultDto, SimulationResult.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(simulationResultService.createSimulationResult(simulationResult).getBody());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimulationResultDto> getSimulationResult(@PathVariable Long id) {
        return ResponseEntity.ok(simulationResultService.getSimulationResultById(id).getBody());
    }

    @GetMapping
    public ResponseEntity<Page<SimulationResultDto>> getAllSimulationResults(Pageable pageable) {
        return ResponseEntity.ok((Page<SimulationResultDto>) simulationResultService.getAllSimulationResult(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SimulationResultDto> updateSimulationResult(@PathVariable Long id, @Valid @RequestBody SimulationResultDto simulationResultDto) {
        SimulationResult simulationResult = modelMapper.map(simulationResultDto, SimulationResult.class);
        simulationResult.setId(id);
        return ResponseEntity.ok(simulationResultService.updateSimulationResult(simulationResult).getBody());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSimulationResult(@PathVariable Long id) {
        simulationResultService.deleteSimulationResultById(id);
        return ResponseEntity.noContent().build();
    }
}
