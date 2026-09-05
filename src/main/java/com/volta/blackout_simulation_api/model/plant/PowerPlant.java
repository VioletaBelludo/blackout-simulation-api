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
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class PowerPlant extends BaseEntity {

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Invalid type. Type cannot be null or empty.")
    private PlantType type;

    @Column(name = "name")
    @NotBlank(message = "Invalid name. Name cannot be null or empty.")
    private String name;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "maxCapacityMW")
    @DecimalMin(value = "0.0", inclusive = false, message = "Invalid max capacity. Capacity cannot be negative.")
    private double maxCapacityMW;

    @Column(name = "restart_initiation_time")
    private LocalDateTime restartInitiationTime;

    @Column(name = "plant_state")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Invalid state. State cannot be null.")
    private PlantState state;
}
