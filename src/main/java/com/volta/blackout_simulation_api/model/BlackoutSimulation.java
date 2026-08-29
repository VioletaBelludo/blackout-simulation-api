package com.volta.blackout_simulation_api.model;

import com.volta.blackout_simulation_api.model.plant.PowerPlant;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "blackout_simulation")
@Getter
@Setter
public class BlackoutSimulation extends BaseEntity {

    @Column(name = "all_plants")
    private List<PowerPlant> allPlants;

    @Column(name = "minute_demands")
    private MinuteDemand[] minuteDemands;
}
