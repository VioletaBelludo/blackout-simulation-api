package com.volta.blackout_simulation_api.service;


import com.volta.blackout_simulation_api.dto.MinuteDemandDto;
import com.volta.blackout_simulation_api.model.MinuteDemand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface MinuteDemandService {
    ResponseEntity<MinuteDemandDto> createMinuteDemand(MinuteDemand minuteDemand);
    ResponseEntity<MinuteDemandDto> updateMinuteDemand(MinuteDemand minuteDemand);
    ResponseEntity<MinuteDemandDto> getMinuteDemandById(Long id);
    Page<MinuteDemandDto> getAllMinuteDemands(Pageable pageable);
    ResponseEntity<Void> deleteMinuteDemandById(Long id);
}
