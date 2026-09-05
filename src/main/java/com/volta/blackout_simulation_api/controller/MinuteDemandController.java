package com.volta.blackout_simulation_api.controller;

import com.volta.blackout_simulation_api.dto.MinuteDemandDto;
import com.volta.blackout_simulation_api.model.MinuteDemand;
import com.volta.blackout_simulation_api.service.MinuteDemandService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/minute_demand")
public class MinuteDemandController {
    private final MinuteDemandService minuteDemandService;
    private final ModelMapper modelMapper;

    public MinuteDemandController(MinuteDemandService minuteDemandService, ModelMapper modelMapper) {
        this.minuteDemandService = minuteDemandService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<MinuteDemandDto> addMinuteDemand(@Valid @RequestBody MinuteDemandDto minuteDemandDto) {
        MinuteDemand minuteDemand = modelMapper.map(minuteDemandDto, MinuteDemand.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(minuteDemandService.createMinuteDemand(minuteDemand).getBody());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MinuteDemandDto> getMinuteDemand(@PathVariable Long id) {
        return ResponseEntity.ok(minuteDemandService.getMinuteDemandById(id).getBody());
    }

    @GetMapping
    public ResponseEntity<Page<MinuteDemandDto>> getAllMinuteDemands(Pageable pageable) {
        return ResponseEntity.ok(minuteDemandService.getAllMinuteDemands(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MinuteDemandDto> updateMinuteDemand(@PathVariable Long id, @Valid @RequestBody MinuteDemandDto minuteDemandDto) {
        MinuteDemand minuteDemand = modelMapper.map(minuteDemandDto, MinuteDemand.class);
        minuteDemand.setId(id);
        return ResponseEntity.ok(minuteDemandService.updateMinuteDemand(minuteDemand).getBody());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMinuteDemand(@PathVariable Long id) {
        minuteDemandService.deleteMinuteDemandById(id);
        return ResponseEntity.noContent().build();
    }
}
