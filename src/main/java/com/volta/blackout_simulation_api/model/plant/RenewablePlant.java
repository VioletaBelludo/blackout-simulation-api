package com.volta.blackout_simulation_api.model.plant;

import com.volta.blackout_simulation_api.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "renewable_plant")
public class RenewablePlant extends PowerPlant {

    @Column(name = "efficiency")
    @DecimalMin(value = "0.0", message = "Invalid efficiency. Efficiency has to be between 0.0 and 1.0.")
    @DecimalMax(value = "1.0", message = "Invalid efficiency. Efficiency has to be between 0.0 and 1.0.")
    private double efficiency;
}
