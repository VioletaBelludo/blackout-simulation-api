package com.volta.blackout_simulation_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "minute_demand")
public class MinuteDemand extends BaseEntity {

    @Column(name = "time")
    @NotNull(message = "Invalid time demand. Time demand cannot be null.")
    private LocalTime time;

    @Column(name = "megawatts")
    @DecimalMin(value = "0", inclusive = false, message = "Invalid megawatts. Megawatts cannot be negative.")
    private double megawatts;

}
