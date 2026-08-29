package com.volta.blackout_simulation_api.model.plant;

import com.volta.blackout_simulation_api.model.BaseEntity;
import com.volta.blackout_simulation_api.model.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "power_plant")
@Getter
@Setter
public class PowerPlant extends BaseEntity {

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Invalid type. Type cannot be null or empty.")
    private PlantType type;

    @Column(name = "name")
    @NotBlank(message = "Invalid name. Name cannot be null or empty.")
    private String name;

    @Column(name = "location")
    @Embedded
    private Location location;

    @Column(name = "maxCapacityMW")
    @DecimalMin(value = "0.0", inclusive = false, message = "Invalid max capacity. Capacity cannot be negative.")
    private double maxCapacityMW;

    @Column(name = "restart_initiation_time")
    private LocalDateTime restartInitiationTime;

    @Column(name = "plant_state")
    @NotNull(message = "Invalid state. State cannot be null.")
    private PlantState state;

    //Especificas
    //Renovable

    //como hago para impedir que  sea null si la planta es renovable
    //@NotBlank(message = "Invalid efficiency. Efficiency has to be between 0.0 and 1.0.")
    @Column(name = "efficiency")
    private double efficiency;

    //No renovable
    //como hago para impedir que sea null si la planta es no renovable renovable
    @Column(name = "thermal_fuel_type")
    @NotBlank(message = "Invalid fuel type. Fuel type cannot be null.")
    private ThermalFuelType thermalFuelType;
}
