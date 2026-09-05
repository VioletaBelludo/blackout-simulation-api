package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.SimulationResultDto;
import com.volta.blackout_simulation_api.model.SimulationResult;
import com.volta.blackout_simulation_api.repository.SimulationResultRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SimulationResultServiceImpl implements SimulationResultService{

    private final SimulationResultRepository simulationResultRepository;
    private final ModelMapper modelMapper;

    public SimulationResultServiceImpl(SimulationResultRepository simulationResultRepository, ModelMapper modelMapper) {
        this.simulationResultRepository = simulationResultRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<SimulationResultDto> createSimulationResult(SimulationResult simulationResult) {
        SimulationResult newSimulationResult = simulationResultRepository.save(simulationResult);
        SimulationResultDto simulationResultDto = modelMapper.map(newSimulationResult, SimulationResultDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(simulationResultDto);
    }

    @Override
    public ResponseEntity<SimulationResultDto> updateSimulationResult(SimulationResult simulationResult) {
        Optional<SimulationResult> existingSimulationResult = simulationResultRepository.findById(simulationResult.getId());

        if (existingSimulationResult.isPresent()){
            SimulationResult simulationResultToUpdate = existingSimulationResult.get();
            simulationResultToUpdate.setId(simulationResult.getId());
            simulationResultToUpdate.setTime(simulationResult.getTime());
            simulationResultToUpdate.setAverageStability(simulationResult.getAverageStability());
            simulationResultToUpdate.setGeneratedMW(simulationResult.getGeneratedMW());
            simulationResultToUpdate.setExpectedDemandMW(simulationResult.getExpectedDemandMW());
            simulationResultToUpdate.setGeneratedByTypeMW(simulationResult.getGeneratedByTypeMW());
            simulationResultRepository.save(simulationResultToUpdate);
            return ResponseEntity.ok(modelMapper.map(simulationResultToUpdate, SimulationResultDto.class));
        }
        return null;
    }

    @Override
    public ResponseEntity<SimulationResultDto> getSimulationResultById(Long id) {
        Optional<SimulationResult> simulationResultOptional = simulationResultRepository.findById(id);
        return simulationResultOptional.map(simulationResult -> ResponseEntity.ok(modelMapper.map(simulationResult, SimulationResultDto.class)))
                .orElse(ResponseEntity.notFound().build());

    }

    @Override
    public Page<SimulationResultDto> getAllSimulationResult(Pageable pageable) {
        List<SimulationResult> allSimulationResults = simulationResultRepository.findAll();
        List<SimulationResultDto> simulationResultDtoList = allSimulationResults.stream().map(simulationResult -> modelMapper
                .map(simulationResult, SimulationResultDto.class)).toList();
        return (org.springframework.data.domain.Page<SimulationResultDto>) ResponseEntity.ok(simulationResultDtoList);
    }

    @Override
    public ResponseEntity<Void> deleteSimulationResultById(Long id) {
        Optional<SimulationResult> optionalSimulationResult = simulationResultRepository.findById(id);
        if(optionalSimulationResult.isPresent()){
            simulationResultRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return null;
    }
}
