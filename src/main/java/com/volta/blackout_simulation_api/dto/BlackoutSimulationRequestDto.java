package com.volta.blackout_simulation_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlackoutSimulationRequestDto {

    @NotNull(message = "Invalid blackout start. Blackout start cannot be null.")
    private LocalDateTime blackoutStart;
}
