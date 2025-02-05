package com.example.building.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BuildingRequest {
    @NotBlank(message = "Building name is required")
    private String name;
    
    @NotBlank(message = "Building location is required")
    private String location;
    
    @NotNull(message = "Current temperature is required")
    private Double currentTemperature;
    
    @NotNull(message = "Target temperature is required")
    private Double targetTemperature;
} 