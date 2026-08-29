package com.volta.blackout_simulation_api.model.plant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ThermalFuelType {NATURAL_GAS("Natural Gas"),
    COAL("Coal"),
    FUEL_OIL("Fuel Oil"),
    BIOMASS("Biomass");

    private final String description;

    ThermalFuelType(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
