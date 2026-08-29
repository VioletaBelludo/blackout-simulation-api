package com.volta.blackout_simulation_api.repository;

import com.volta.blackout_simulation_api.model.BlackoutSimulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackoutSimulationRepository extends JpaRepository<BlackoutSimulation, Long> {
}
