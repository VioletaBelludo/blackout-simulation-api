package com.volta.blackout_simulation_api.model.plant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thermal_plant")
public class ThermalPlant extends PowerPlant {

    @Column(name = "thermal_fuel_type")
    @NotBlank(message = "Invalid fuel type. Fuel type cannot be null.")
    private ThermalFuelType thermalFuelType;
}
