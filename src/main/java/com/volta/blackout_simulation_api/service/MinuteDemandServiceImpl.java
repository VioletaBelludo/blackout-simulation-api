package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.MinuteDemandDto;
import com.volta.blackout_simulation_api.model.MinuteDemand;
import com.volta.blackout_simulation_api.repository.MinuteDemandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MinuteDemandServiceImpl implements  MinuteDemandService{

    private final MinuteDemandRepository minuteDemandRepository;
    private final ModelMapper modelMapper;

    public MinuteDemandServiceImpl(MinuteDemandRepository minuteDemandRepository, ModelMapper modelMapper) {
        this.minuteDemandRepository = minuteDemandRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<MinuteDemandDto> createMinuteDemand(MinuteDemand minuteDemand) {
        MinuteDemand newMinuteDemand = minuteDemandRepository.save(minuteDemand);
        MinuteDemandDto minuteDemandDto = modelMapper.map(newMinuteDemand, MinuteDemandDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(minuteDemandDto);
    }

    @Override
    public ResponseEntity<MinuteDemandDto> updateMinuteDemand(MinuteDemand minuteDemand) {
        Optional<MinuteDemand> existingMinuteDemand = minuteDemandRepository.findById(minuteDemand.getId());

        if (existingMinuteDemand.isPresent()){
            MinuteDemand minuteDemandToUpdate = existingMinuteDemand.get();
            minuteDemandToUpdate.setId(minuteDemand.getId());
            minuteDemandToUpdate.setTime(minuteDemand.getTime());
            minuteDemandToUpdate.setMegawatts(minuteDemand.getMegawatts());
            minuteDemandRepository.save(minuteDemandToUpdate);
            return ResponseEntity.ok(modelMapper.map(minuteDemandToUpdate, MinuteDemandDto.class));
        }
        return null;
    }

    @Override
    public ResponseEntity<MinuteDemandDto> getMinuteDemandById(Long id) {
        Optional<MinuteDemand> minuteDemandOptional = minuteDemandRepository.findById(id);
        return minuteDemandOptional.map(minuteDemand -> ResponseEntity.ok(modelMapper.map(minuteDemand, MinuteDemandDto.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Page<MinuteDemandDto> getAllMinuteDemands(Pageable pageable) {
        return minuteDemandRepository.findAll(pageable)
                .map(minuteDemand -> modelMapper.map(minuteDemand, MinuteDemandDto.class));
    }

    @Override
    public ResponseEntity<Void> deleteMinuteDemandById(Long id) {
        Optional<MinuteDemand> optionalMinuteDemand = minuteDemandRepository.findById(id);
        if(optionalMinuteDemand.isPresent()){
            minuteDemandRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return null;
    }
}
