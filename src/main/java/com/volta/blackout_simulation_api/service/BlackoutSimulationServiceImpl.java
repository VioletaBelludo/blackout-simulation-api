package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.model.*;
import com.volta.blackout_simulation_api.model.plant.PlantState;
import com.volta.blackout_simulation_api.model.plant.PlantType;
import com.volta.blackout_simulation_api.model.plant.PowerPlant;
import com.volta.blackout_simulation_api.model.plant.RenewablePlant;
import com.volta.blackout_simulation_api.repository.BlackoutSimulationRepository;
import com.volta.blackout_simulation_api.repository.MinuteDemandRepository;
import com.volta.blackout_simulation_api.repository.PowerPlantRepository;
import com.volta.blackout_simulation_api.repository.SimulationResultRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlackoutSimulationServiceImpl implements BlackoutSimulationService{
    private static final double MIN_REQUIRED_STABILITY = 0.7;
    private static final int SIMULATION_DAYS = 36;

    private final PowerPlantRepository powerPlantRepository;
    private final MinuteDemandRepository minuteDemandRepository;
    private final SimulationResultRepository simulationResultRepository;
    private final BlackoutSimulationRepository blackoutSimulationRepository;


    public BlackoutSimulationServiceImpl(PowerPlantRepository powerPlantRepository,
                                         MinuteDemandRepository minuteDemandRepository, SimulationResultRepository simulationResultRepository, BlackoutSimulationRepository blackoutSimulationRepository) {
        this.powerPlantRepository = powerPlantRepository;
        this.minuteDemandRepository = minuteDemandRepository;
        this.simulationResultRepository = simulationResultRepository;
        this.blackoutSimulationRepository = blackoutSimulationRepository;
    }

    @Override
    @Transactional
    public List<SimulationResult> runSimulation(LocalDateTime blackoutStart) {
        List<SimulationResult> results = new ArrayList<>();
        List<PowerPlant> powerPlants = powerPlantRepository.findAll();
        List<MinuteDemand> minuteDemands = minuteDemandRepository.findAll();

        initializeBlackout(powerPlants, blackoutStart);

        for (int minute = 0; minute < SIMULATION_DAYS * 60; minute++) {
            LocalDateTime currentTime = blackoutStart.plusMinutes(minute);
            LocalTime currentTimeOfDay = currentTime.toLocalTime();
            int index = (currentTimeOfDay.toSecondOfDay()) / 60 % 1440;
            double currentDemand = minuteDemands.get(index).getMegawatts();

            updatePlantStates(powerPlants, currentTime);
            List<PowerPlant> availablePlants = getAvailableOnlinePlants(powerPlants, currentTimeOfDay);

            // Prioritize renewables by efficiency (descending), maintaining original order if tied
            List<PowerPlant> sortedPlants = availablePlants.stream()
                    .sorted((a, b) -> {
                        boolean aRen = a.getType().isRenewable();
                        boolean bRen = b.getType().isRenewable();

                        if (aRen && bRen) {
                            double effA = ((RenewablePlant) a).getEfficiency();
                            double effB = ((RenewablePlant) b).getEfficiency();
                            return Double.compare(effB, effA); // Most efficient first
                        } else if (aRen) {
                            return -1;
                        } else if (bRen) {
                            return 1;
                        }
                        return 0;
                    }).toList();

            Map<PowerPlant, Double> generatedByPlant = new LinkedHashMap<>();
            double totalGenerated = 0;

            for (PowerPlant plant : sortedPlants) {
                if (totalGenerated >= currentDemand) break;

                double capacity = (plant instanceof RenewablePlant renewablePlant)
                        ? plant.getMaxCapacityMW() * renewablePlant.getEfficiency()
                        : plant.getMaxCapacityMW();

                double remaining = currentDemand - totalGenerated;
                double toUse = Math.min(remaining, capacity);
                generatedByPlant.put(plant, toUse);
                totalGenerated += toUse;
            }

            // Proportional adjustment if overproducing
            if (totalGenerated > currentDemand) {
                double scale = currentDemand / totalGenerated;
                Map<PowerPlant, Double> scaledMap = new LinkedHashMap<>();
                for (Map.Entry<PowerPlant, Double> entry : generatedByPlant.entrySet()) {
                    scaledMap.put(entry.getKey(), entry.getValue() * scale);
                }
                generatedByPlant.clear();
                generatedByPlant.putAll(scaledMap);
                totalGenerated = currentDemand;
            }

            // Calculate current stability
            double currentStability = totalGenerated != 0.0
                    ? calculateWeightedStabilityByPlant(generatedByPlant) / totalGenerated
                    : 0.0;

            // If minimum stability is not reached, adjust:
            if (currentStability < MIN_REQUIRED_STABILITY) {
                // 1. Remove renewables starting from the least stable
                List<Map.Entry<PowerPlant, Double>> renewablesSorted = generatedByPlant.entrySet().stream()
                        .filter(e -> e.getKey().getType().isRenewable())
                        .sorted(Comparator.comparingDouble(e -> e.getKey().getType().getStability()))
                        .toList();

                for (Map.Entry<PowerPlant, Double> entry : renewablesSorted) {
                    PowerPlant renewable = entry.getKey();
                    double removed = entry.getValue();
                    generatedByPlant.remove(renewable);
                    totalGenerated -= removed;
                    currentStability = totalGenerated != 0.0
                            ? calculateWeightedStabilityByPlant(generatedByPlant) / totalGenerated
                            : 0.0;
                    if (currentStability >= MIN_REQUIRED_STABILITY) break;
                }

                // 2. Add nuclear energy
                List<PowerPlant> nuclear = availablePlants.stream()
                        .filter(p -> p.getType().equals(PlantType.NUCLEAR) && !generatedByPlant.containsKey(p))
                        .collect(Collectors.toList());
                totalGenerated = addGeneration(nuclear, generatedByPlant, currentDemand, false, true);

                // 3. If still insufficient, add thermal (ordered by stability)
                currentStability = totalGenerated != 0.0
                        ? calculateWeightedStabilityByPlant(generatedByPlant) / totalGenerated
                        : 0.0;
                if (currentStability < MIN_REQUIRED_STABILITY) {
                    List<PowerPlant> thermals = availablePlants.stream()
                            .filter(p -> !p.getType().isRenewable())
                            .filter(p -> !p.getType().equals(PlantType.NUCLEAR))
                            .filter(p -> !generatedByPlant.containsKey(p))
                            .sorted(Comparator.comparingDouble((PowerPlant p) -> p.getType().getStability()).reversed())
                            .collect(Collectors.toList());

                    totalGenerated = addGeneration(thermals, generatedByPlant, currentDemand, false, false);
                }
            }

            Map<PlantType, Double> generatedByType = new HashMap<>();
            for (Map.Entry<PowerPlant, Double> entry : generatedByPlant.entrySet()) {
                generatedByType.merge(entry.getKey().getType(), entry.getValue(), Double::sum);
            }

            double averageStability = totalGenerated != 0.0
                    ? calculateWeightedStabilityByPlant(generatedByPlant) / totalGenerated
                    : 0.0;

            results.add(new SimulationResult(currentTime, totalGenerated, currentDemand, averageStability, generatedByType));
        }

        List<SimulationResult> savedResults = simulationResultRepository.saveAll(results);

        BlackoutSimulation blackoutSimulation = new BlackoutSimulation(powerPlants, minuteDemands);
        blackoutSimulationRepository.save(blackoutSimulation);

        return savedResults;
    }


    private void initializeBlackout(List<PowerPlant> powerPlants, LocalDateTime blackoutStart) {
        for (PowerPlant powerPlant : powerPlants) {
            powerPlant.setState(PlantState.OFFLINE);
            powerPlant.setRestartInitiationTime(blackoutStart);
        }
        powerPlantRepository.saveAll(powerPlants);
    }

    private void updatePlantStates(List<PowerPlant> powerPlants, LocalDateTime currentTime) {
        for (PowerPlant powerPlant : powerPlants) {
            if (powerPlant.getState() == PlantState.OFFLINE &&
                    powerPlant.getRestartInitiationTime().plus(powerPlant.getType().getRestartDuration()).isBefore(currentTime)) {
                powerPlant.setState(PlantState.ONLINE);
            }
        }
    }

    private List<PowerPlant> getAvailableOnlinePlants(List<PowerPlant> powerPlants, LocalTime time) {
        return powerPlantRepository.findAll().stream().filter(p -> p.getState() == PlantState.ONLINE)
                .filter(p -> !time.isBefore(p.getType().getAvailableFromTime()) && !time.isAfter(p.getType().getAvailableToTime()))
                .collect(Collectors.toList());
    }

    private double addGeneration(List<PowerPlant> plants, Map<PowerPlant, Double> map, double demand,
                                 boolean onlyRenewables, boolean onlyNuclear) {

        double total = map.values().stream().mapToDouble(Double::doubleValue).sum();

        for (PowerPlant plant : plants) {
            if (map.containsKey(plant)) continue;

            boolean isRenewable = plant.getType().isRenewable();
            boolean isNuclear = plant.getType().equals(PlantType.NUCLEAR);

            if (onlyRenewables && !isRenewable) continue;
            if (onlyNuclear && !isNuclear) continue;
            if (!onlyRenewables && !onlyNuclear && (isRenewable || isNuclear)) continue;

            double capacity = (plant instanceof RenewablePlant renewablePlant)
                    ? plant.getMaxCapacityMW() * renewablePlant.getEfficiency()
                    : plant.getMaxCapacityMW();
            double remaining = demand - total;
            if (remaining <= 0) break;

            double toUse = Math.min(remaining, capacity);
            map.put(plant, toUse);
            total += toUse;
        }
        return total;
    }

    private double calculateWeightedStabilityByPlant(Map<PowerPlant, Double> map){
        return map.entrySet().stream()
                .mapToDouble(e -> e.getKey().getType().getStability() * e.getValue())
                .sum();
    }


}
