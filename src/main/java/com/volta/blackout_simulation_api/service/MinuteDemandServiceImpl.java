package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.MinuteDemandDto;
import com.volta.blackout_simulation_api.model.MinuteDemand;
import com.volta.blackout_simulation_api.repository.MinuteDemandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MinuteDemandServiceImpl implements  MinuteDemandService{

    private final MinuteDemandRepository minuteDemandRepository;

    private final ModelMapper modelMapper;

    public MinuteDemandServiceImpl(MinuteDemandRepository minuteDemandRepository, ModelMapper modelMapper) {
        this.minuteDemandRepository = minuteDemandRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MinuteDemandDto createMinuteDemand(MinuteDemand minuteDemand) {
        return null;
    }

    @Override
    public MinuteDemandDto updateMinuteDemand(MinuteDemand minuteDemand) {
        return null;
    }

    @Override
    public MinuteDemandDto saveMinuteDemand(MinuteDemand minuteDemand) {
        return null;
    }

    @Override
    public MinuteDemandDto deleteMinuteDemand(MinuteDemand minuteDemand) {
        return null;
    }
}
