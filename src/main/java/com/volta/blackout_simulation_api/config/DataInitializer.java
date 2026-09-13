package com.volta.blackout_simulation_api.config;

import com.volta.blackout_simulation_api.model.Location;
import com.volta.blackout_simulation_api.model.MinuteDemand;
import com.volta.blackout_simulation_api.model.plant.*;
import com.volta.blackout_simulation_api.repository.LocationRepository;
import com.volta.blackout_simulation_api.repository.MinuteDemandRepository;
import com.volta.blackout_simulation_api.repository.PowerPlantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.swing.text.TabExpander;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.volta.blackout_simulation_api.model.plant.ThermalFuelType.FUEL_OIL;
import static com.volta.blackout_simulation_api.model.plant.ThermalFuelType.NATURAL_GAS;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PowerPlantRepository powerPlantRepository;
    private final LocationRepository locationRepository;
    private final MinuteDemandRepository minuteDemandRepository;

    @Override
    public void run(String... args) {
        loadPowerPlants();
        loadMinuteDemand();
    }

    private void loadPowerPlants() {
        if (powerPlantRepository.count() > 0) {
            log.info("Las plantas de energía ya están inicializadas.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new ClassPathResource("power_plants.csv").getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Saltar encabezados: Type,Name,Latitude,Longitude,City,Max_Capacity_MW
                }

                String[] data = line.split(",");
                PlantType plantType = PlantType.valueOf(data[0].trim());

                // 1. Guardar primero la ubicación (Location)
                Location location = new Location();
                location.setLatitude(Double.parseDouble(data[2].trim()));
                location.setLongitude(Double.parseDouble(data[3].trim()));
                location.setCity(data[4].trim());
                location = locationRepository.save(location);

                // 2. Instanciar la clase hija correspondiente
                PowerPlant plant = createPlantInstance(plantType);

                // 3. Asignar los atributos heredados de PowerPlant (Ajusta los setters según tus atributos)
                plant.setType(plantType);
                plant.setName(data[1].trim());
                plant.setLocation(location);
                plant.setMaxCapacityMW(Double.parseDouble(data[5].trim()));
                plant.setState(PlantState.ONLINE);

                powerPlantRepository.save(plant);
            }
            log.info("Plantas de energía cargadas con éxito.");
        } catch (Exception e) {
            log.error("Error al cargar power_plants.csv", e);
        }
    }

    private void loadMinuteDemand() {
        if (minuteDemandRepository.count() > 0) {
            log.info("La previsión de demanda ya está inicializada.");
            return;
        }

        // Formato H:mm para leer horas con un solo dígito como "0:00" o "9:00"
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        List<MinuteDemand> demands = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new ClassPathResource("minute_forecast.csv").getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 2) continue;

                MinuteDemand demand = new MinuteDemand();
                demand.setTime(LocalTime.parse(data[0].trim(), timeFormatter));
                demand.setMegawatts(Double.parseDouble(data[1].trim()));

                demands.add(demand);
            }
            minuteDemandRepository.saveAll(demands);
            log.info("Previsión de demanda cargada con éxito.");

        } catch (Exception e) {
            log.error("Error al cargar minute_forecast.csv", e);
        }
    }

    // --- MÉTODOS AUXILIARES PARA POLIMORFISMO ---

    private PowerPlant createPlantInstance(PlantType plantType) {
        switch (plantType) {
            case NUCLEAR:
                return new NuclearPlant();

            case HYDRO:
            case WIND:
            case SOLAR:
            case GEOTHERMAL:
                RenewablePlant renewable = new RenewablePlant();
                // Según tu pantallazo, la eficiencia debe estar entre 0.0 y 1.0 por validación
                renewable.setEfficiency(1.0);
                return renewable;

            case COAL:
            case COMBINED_CYCLE:
            case BIOMASS:
            case FUEL_GAS:
                ThermalPlant thermal = new ThermalPlant();
                thermal.setThermalFuelType(mapThermalFuelType(plantType));
                return thermal;

            default:
                throw new IllegalArgumentException("Tipo de planta desconocido: " + plantType);
        }
    }

    private ThermalFuelType mapThermalFuelType(PlantType plantType) {
        switch (plantType) {
            case COMBINED_CYCLE: return  ThermalFuelType.NATURAL_GAS;
            case COAL: return ThermalFuelType.COAL;
            case BIOMASS: return ThermalFuelType.BIOMASS;
            case FUEL_GAS: return FUEL_OIL;
        }
    }
}
