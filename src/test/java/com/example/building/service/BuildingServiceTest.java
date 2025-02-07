package com.example.building.service;

import com.example.building.dto.BuildingRequest;
import com.example.building.mapper.BuildingMapper;
import com.example.building.model.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for BuildingService class.
 * Tests all business logic including building management and temperature control.
 */
class BuildingServiceTest {

    @Mock
    private BuildingMapper buildingMapper;

    @InjectMocks
    private BuildingService buildingService;

    private Building testBuilding;
    private BuildingRequest testRequest;

    /**
     * Set up test data before each test case.
     * Initializes mocks and creates test building and request objects.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testBuilding = new Building();
        testBuilding.setId(1L);
        testBuilding.setName("Test Building");
        testBuilding.setLocation("Test Location");
        testBuilding.setCurrentTemperature(22.0);
        testBuilding.setTargetTemperature(24.0);
        testBuilding.setStatus("HEATING");
        testBuilding.setLastUpdated(LocalDateTime.now());
        testBuilding.setCreateTime(LocalDateTime.now());

        testRequest = new BuildingRequest();
        testRequest.setName("Test Building");
        testRequest.setLocation("Test Location");
        testRequest.setCurrentTemperature(22.0);
        testRequest.setTargetTemperature(24.0);
    }

    /**
     * Test creating a new building.
     * Verifies that:
     * 1. The building is created with correct properties
     * 2. Status is set based on temperature difference
     * 3. Timestamps are set
     * 4. The mapper's insert method is called
     */
    @Test
    void createBuilding_Success() {
        when(buildingMapper.insert(any(Building.class))).thenReturn(1);

        Building result = buildingService.createBuilding(testRequest);

        assertNotNull(result);
        assertEquals(testRequest.getName(), result.getName());
        assertEquals(testRequest.getLocation(), result.getLocation());
        assertEquals(testRequest.getCurrentTemperature(), result.getCurrentTemperature());
        assertEquals(testRequest.getTargetTemperature(), result.getTargetTemperature());
        assertEquals("HEATING", result.getStatus());
        assertNotNull(result.getLastUpdated());
        assertNotNull(result.getCreateTime());

        verify(buildingMapper).insert(any(Building.class));
    }

    /**
     * Test retrieving an existing building by ID.
     * Verifies that:
     * 1. The correct building is returned
     * 2. The mapper's findById method is called
     */
    @Test
    void getBuilding_Success() {
        when(buildingMapper.findById(1L)).thenReturn(testBuilding);

        Building result = buildingService.getBuilding(1L);

        assertNotNull(result);
        assertEquals(testBuilding.getId(), result.getId());
        assertEquals(testBuilding.getName(), result.getName());

        verify(buildingMapper).findById(1L);
    }

    /**
     * Test retrieving a non-existent building.
     * Verifies that:
     * 1. Null is returned when building doesn't exist
     * 2. The mapper's findById method is called
     */
    @Test
    void getBuilding_NotFound() {
        when(buildingMapper.findById(999L)).thenReturn(null);

        Building result = buildingService.getBuilding(999L);

        assertNull(result);

        verify(buildingMapper).findById(999L);
    }

    /**
     * Test retrieving all buildings.
     * Verifies that:
     * 1. The list of buildings is returned
     * 2. The mapper's findAll method is called
     */
    @Test
    void getAllBuildings_Success() {
        List<Building> buildings = Arrays.asList(testBuilding);
        when(buildingMapper.findAll()).thenReturn(buildings);

        List<Building> result = buildingService.getAllBuildings();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testBuilding.getId(), result.get(0).getId());

        verify(buildingMapper).findAll();
    }

    /**
     * Test updating a building's target temperature.
     * Verifies that:
     * 1. The temperature is updated
     * 2. Status is recalculated
     * 3. Last updated timestamp is set
     * 4. The mapper's update method is called
     */
    @Test
    void updateTemperature_Success() {
        when(buildingMapper.findById(1L)).thenReturn(testBuilding);
        when(buildingMapper.update(any(Building.class))).thenReturn(1);

        Building result = buildingService.updateTemperature(1L, 25.0);

        assertNotNull(result);
        assertEquals(25.0, result.getTargetTemperature());
        assertNotNull(result.getLastUpdated());

        verify(buildingMapper).findById(1L);
        verify(buildingMapper).update(any(Building.class));
    }

    /**
     * Test updating temperature for a non-existent building.
     * Verifies that:
     * 1. Null is returned when building doesn't exist
     * 2. The mapper's update method is not called
     */
    @Test
    void updateTemperature_NotFound() {
        when(buildingMapper.findById(999L)).thenReturn(null);

        Building result = buildingService.updateTemperature(999L, 25.0);

        assertNull(result);

        verify(buildingMapper).findById(999L);
        verify(buildingMapper, never()).update(any(Building.class));
    }

    /**
     * Test deleting a building.
     * Verifies that:
     * 1. The mapper's deleteById method is called
     */
    @Test
    void deleteBuilding_Success() {
        when(buildingMapper.deleteById(1L)).thenReturn(1);

        buildingService.deleteBuilding(1L);

        verify(buildingMapper).deleteById(1L);
    }

    /**
     * Test status determination when current temperature equals target.
     * Verifies that status is set to "MAINTAINING" when temperatures match.
     */
    @Test
    void determineStatus_Maintaining() {
        testRequest.setCurrentTemperature(24.0);
        testRequest.setTargetTemperature(24.0);

        Building result = buildingService.createBuilding(testRequest);

        assertEquals("MAINTAINING", result.getStatus());
    }

    /**
     * Test status determination when heating is needed.
     * Verifies that status is set to "HEATING" when current temperature is below target.
     */
    @Test
    void determineStatus_Heating() {
        testRequest.setCurrentTemperature(22.0);
        testRequest.setTargetTemperature(24.0);

        Building result = buildingService.createBuilding(testRequest);

        assertEquals("HEATING", result.getStatus());
    }

    /**
     * Test status determination when cooling is needed.
     * Verifies that status is set to "COOLING" when current temperature is above target.
     */
    @Test
    void determineStatus_Cooling() {
        testRequest.setCurrentTemperature(26.0);
        testRequest.setTargetTemperature(24.0);

        Building result = buildingService.createBuilding(testRequest);

        assertEquals("COOLING", result.getStatus());
    }
}
