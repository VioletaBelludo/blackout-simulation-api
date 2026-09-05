package com.volta.blackout_simulation_api.model.plant;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
//@AllArgsConstructor

@Entity
@Table(name = "nuclear_plant")
@Getter
@Setter
public class NuclearPlant extends PowerPlant {

}
