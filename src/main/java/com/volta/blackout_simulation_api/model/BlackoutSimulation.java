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

    @ManyToMany
    @JoinTable(
            name = "simulation_power_plants",
            joinColumns = @JoinColumn(name = "simulation_id"),
            inverseJoinColumns = @JoinColumn(name = "power_plant_id")
    )
    private List<PowerPlant> allPlants;

    @ManyToMany
    @JoinTable(
            name = "simulation_minute_demands",
            joinColumns = @JoinColumn(name = "minute_demands_id"),
            inverseJoinColumns = @JoinColumn(name = "minute_demands_id")
    )
    private MinuteDemand[] minuteDemands;
}
