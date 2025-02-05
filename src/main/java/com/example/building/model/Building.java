package com.example.building.model;

// This class represents a building entity in our Temperature Control System.
// It stores basic details as well as temperature information.

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Building {
    // Unique identifier for the building
    private Long id;
    
    // The name of the building
    private String name;
    
    // The physical location of the building
    private String location;
    
    // The current temperature reading in the building
    private Double currentTemperature;
    
    // The desired target temperature
    private Double targetTemperature;
    
    // The current status, e.g., HEATING, COOLING, or MAINTAINING
    private String status;
    
    // The timestamp when the temperature or status was last updated
    private LocalDateTime lastUpdated;
    
    // The timestamp when the building record was created
    private LocalDateTime createTime;
} 