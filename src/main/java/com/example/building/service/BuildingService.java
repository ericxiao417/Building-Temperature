package com.example.building.service;

import com.example.building.dto.BuildingRequest;
import com.example.building.mapper.BuildingMapper;
import com.example.building.model.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BuildingService {
    @Autowired
    private BuildingMapper buildingMapper;

    public Building createBuilding(BuildingRequest request) {
        Building building = new Building();
        building.setName(request.getName());
        building.setLocation(request.getLocation());
        building.setCurrentTemperature(request.getCurrentTemperature());
        building.setTargetTemperature(request.getTargetTemperature());
        building.setStatus(determineStatus(request.getCurrentTemperature(), request.getTargetTemperature()));
        building.setLastUpdated(LocalDateTime.now());
        building.setCreateTime(LocalDateTime.now());
        
        buildingMapper.insert(building);
        return building;
    }

    public Building getBuilding(Long id) {
        return buildingMapper.findById(id);
    }

    public List<Building> getAllBuildings() {
        return buildingMapper.findAll();
    }

    public Building updateTemperature(Long id, Double targetTemperature) {
        Building building = buildingMapper.findById(id);
        if (building != null) {
            building.setTargetTemperature(targetTemperature);
            building.setStatus(determineStatus(building.getCurrentTemperature(), targetTemperature));
            building.setLastUpdated(LocalDateTime.now());
            buildingMapper.update(building);
        }
        return building;
    }

    public void deleteBuilding(Long id) {
        buildingMapper.deleteById(id);
    }

    private String determineStatus(Double currentTemp, Double targetTemp) {
        double threshold = 0.5; // Temperature difference threshold
        if (Math.abs(currentTemp - targetTemp) <= threshold) {
            return "MAINTAINING";
        }
        return currentTemp < targetTemp ? "HEATING" : "COOLING";
    }
} 