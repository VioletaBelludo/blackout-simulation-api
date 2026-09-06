package com.volta.blackout_simulation_api.model.plant;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "nuclear_plant")
public class NuclearPlant extends PowerPlant {

}
