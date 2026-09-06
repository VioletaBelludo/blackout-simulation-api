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
public class SimulationResult extends BaseEntity{

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "generatedMW")
    private double generatedMW;

    @Column(name = "expectedDemandMW")
    private double expectedDemandMW;

    @Column(name = "averageStability")
    private double averageStability;

    @ElementCollection
    @CollectionTable(
            name = "simulation_generated_by_type",
            joinColumns = @JoinColumn(name = "simulation_result_id")
    )
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "plant_type")
    @Column(name = "generatedByTypeMW")
    private Map<PlantType, Double> generatedByTypeMW;
}
