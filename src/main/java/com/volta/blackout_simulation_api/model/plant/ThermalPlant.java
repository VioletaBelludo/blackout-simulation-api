package com.volta.blackout_simulation_api.model.plant;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thermal_plant")
public class ThermalPlant extends PowerPlant {

    @Column(name = "thermal_fuel_type")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Invalid fuel type. Fuel type cannot be null.")
    private ThermalFuelType thermalFuelType;
}
