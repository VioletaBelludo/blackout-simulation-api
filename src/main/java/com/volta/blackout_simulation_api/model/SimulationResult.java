package com.volta.blackout_simulation_api.model;

import com.volta.blackout_simulation_api.model.plant.PlantType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "simulation_result")
@Getter
@Setter
public class SimulationResult extends BaseEntity{

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "generatedMW")
    private double generatedMW;

    @Column(name = "expectedDemandMW")
    private double expectedDemandMW;

    @Column(name = "averageStability")
    private double averageStability;

    @Column(name = "generatedByTypeMW")
    private Map<PlantType, Double> generatedByTypeMW;
}
