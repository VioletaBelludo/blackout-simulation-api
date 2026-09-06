package com.volta.blackout_simulation_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "location")
public class Location extends BaseEntity {

    @Column(name = "latitude")
    @NotNull
    @DecimalMin(value = "-90.0", inclusive = false, message = "Invalid latitude value. Latitude must be over -90")
    @DecimalMax(value = "90.0", inclusive = false, message = "Invalid latitude value. Latitude must be under 90")
    private double latitude;

    @Column(name = "longitude")
    @NotNull
    @DecimalMin(value = "-180.0", inclusive = false, message = "Invalid longitude value. Longitude must be over -180")
    @DecimalMax(value = "180.0", inclusive = false, message = "Invalid longitude value. Longitude must be under 180")
    private double longitude;

    @Column(name = "city")
    @NotBlank(message = "City cannot be null or empty.")
    private String city;

}
