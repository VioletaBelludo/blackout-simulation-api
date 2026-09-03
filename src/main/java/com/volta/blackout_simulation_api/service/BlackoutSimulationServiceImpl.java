package com.volta.blackout_simulation_api.service;

import com.volta.blackout_simulation_api.dto.BlackoutSimulationDto;
import com.volta.blackout_simulation_api.model.*;
import com.volta.blackout_simulation_api.model.plant.PlantState;
import com.volta.blackout_simulation_api.model.plant.PlantType;
import com.volta.blackout_simulation_api.model.plant.PowerPlant;
import com.volta.blackout_simulation_api.repository.MinuteDemandRepository;
import com.volta.blackout_simulation_api.repository.PowerPlantRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlackoutSimulationServiceImpl implements BlackoutSimulationService{
    private PowerPlantRepository powerPlantRepository;
    private List<PowerPlant> powerPlants = powerPlantRepository.findAll();
    private MinuteDemandRepository minuteDemandRepository;
    private List<MinuteDemand> minuteDemands =  minuteDemandRepository.findAll();
    //Tengo que cambiarla de sitio
    private static final double MIN_REQUIRED_STABILITY = 0.7;

    public List<SimulationResult> runSimulation(LocalDateTime blackoutStart){
        List<SimulationResult> results = new ArrayList<>();
        initializeBlackout(blackoutStart);

        for (int minute = 0; minute < 36 * 60; minute++){
            LocalDateTime currentTime = blackoutStart.plusMinutes(minute);
            LocalTime currentTimeOfDay = currentTime.toLocalTime();
            int index = (currentTimeOfDay.toSecondOfDay()) / 60 % 1440;
            double currentDemand = minuteDemands.get(index).getMegawatts();

            updatePlantStates(currentTime);
            List<PowerPlant> availablePlants = getAvailableOnlinePlants(currentTimeOfDay);

            // Prioritize renewables by efficiency (descending), maintaining original order if tied
            List<PowerPlant> sortedPlants = availablePlants.stream()
                    .sorted((a, b) -> {
                        boolean aRen = a.getType().isRenewable();
                        boolean bRen = b.getType().isRenewable();

                        if (aRen && bRen) {
                            double effA = a.getEfficiency();
                            double effB = b.getEfficiency();
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

                double capacity = plant.getType().isRenewable()
                        ? plant.getMaxCapacityMW() * plant.getEfficiency()
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
            double currentStability = calculateWeightedStabilityByPlant(generatedByPlant) / totalGenerated;

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
                    currentStability = calculateWeightedStabilityByPlant(generatedByPlant) / totalGenerated;
                    if (currentStability >= MIN_REQUIRED_STABILITY) break;
                }

                // 2. Add nuclear energy
                List<PowerPlant> nuclear = availablePlants.stream()
                        .filter(p -> p.getType().equals(PlantType.NUCLEAR) && !generatedByPlant.containsKey(p))
                        .collect(Collectors.toList());
                totalGenerated = addGeneration(nuclear, generatedByPlant, currentDemand, false, true);

                // 3. If still insufficient, add thermal (ordered by stability)
                currentStability = calculateWeightedStabilityByPlant(generatedByPlant) / totalGenerated;
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

            double averageStability = totalGenerated != 0.0 ? calculateWeightedStabilityByPlant(generatedByPlant) / totalGenerated : 0.0;

            results.add(new SimulationResult(currentTime, totalGenerated, currentDemand, averageStability, generatedByType));
        }
        return results;
    }

    private void initializeBlackout(LocalDateTime blackoutStart) {
        for (int i = 0; i < powerPlants.size(); i++){
            powerPlants.get(i).setState(PlantState.OFFLINE);
            powerPlants.get(i).setRestartInitiationTime(blackoutStart);
        }
    }

    private void updatePlantStates(LocalDateTime currentTime){
        for (int i = 0; i < powerPlants.size(); i++) {
            if (powerPlants.get(i).getState() == PlantState.OFFLINE &&
                    powerPlants.get(i).getRestartInitiationTime().plus(powerPlants.get(i).getType().getRestartDuration()).isBefore(currentTime)){
                powerPlants.get(i).setState(PlantState.ONLINE);
            }
        }
    }

    private List<PowerPlant> getAvailableOnlinePlants(LocalTime time) {
        return powerPlantRepository.findAll().stream().filter(p -> p.getState() == PlantState.ONLINE)
                .filter(p -> !time.isBefore(p.getType().getAvailableFromTime()) && !time.isAfter(p.getType().getAvailableToTime()))
                .collect(Collectors.toList());
    }

    private double addGeneration(List<PowerPlant> plants, Map<PowerPlant, Double> map, double demand,
                                 boolean onlyRenewables, boolean onlyNuclear){
        double total = map.values().stream().mapToDouble(Double::doubleValue).sum();
        for (int i = 0; i < powerPlants.size(); i++){
            if (map.containsKey(powerPlants.get(i))) continue;

            if(((onlyRenewables && !powerPlants.get(i).getType().isRenewable())
                    || (onlyNuclear && !(powerPlants.get(i).getType().equals(PlantType.NUCLEAR))
                    || (!onlyRenewables && !onlyNuclear && (powerPlants.get(i).getType().isRenewable() || powerPlants.get(i).getType().equals(PlantType.NUCLEAR)))))){
                continue;
            }

            double capacity = powerPlants.get(i).getType().isRenewable()
                    ? powerPlants.get(i).getMaxCapacityMW() * powerPlants.get(i).getEfficiency(): powerPlants.get(i).getMaxCapacityMW();
            double remaining = demand - total;
            if (remaining <= 0) break;

            double toUse = Math.min(remaining, capacity);
            map.put(powerPlants.get(i), toUse);
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
