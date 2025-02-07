package com.example.building.controller;

import com.example.building.dto.BuildingRequest;
import com.example.building.model.Building;
import com.example.building.service.BuildingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for BuildingController class.
 * Tests all REST endpoints and their responses.
 */
class BuildingControllerTest {

    @Mock
    private BuildingService buildingService;

    @InjectMocks
    private BuildingController buildingController;

    private Building testBuilding;
    private BuildingRequest testRequest;

    /**
     * Set up test data before each test case.
     * Initializes mocks and creates test building and request objects.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test building
        testBuilding = new Building();
        testBuilding.setId(1L);
        testBuilding.setName("Test Building");
        testBuilding.setLocation("Test Location");
        testBuilding.setCurrentTemperature(22.0);
        testBuilding.setTargetTemperature(24.0);
        testBuilding.setStatus("HEATING");
        testBuilding.setLastUpdated(LocalDateTime.now());
        testBuilding.setCreateTime(LocalDateTime.now());

        // Setup test request
        testRequest = new BuildingRequest();
        testRequest.setName("Test Building");
        testRequest.setLocation("Test Location");
        testRequest.setCurrentTemperature(22.0);
        testRequest.setTargetTemperature(24.0);
    }

    /**
     * Test creating a new building.
     * Verifies that:
     * 1. The controller returns HTTP 200 OK
     * 2. The response body contains the correct building details
     * 3. The building service's createBuilding method is called
     */
    @Test
    void createBuilding_Success() {
        when(buildingService.createBuilding(any(BuildingRequest.class))).thenReturn(testBuilding);

        ResponseEntity<Building> response = buildingController.createBuilding(testRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testBuilding.getName(), response.getBody().getName());
        assertEquals(testBuilding.getLocation(), response.getBody().getLocation());
        assertEquals(testBuilding.getCurrentTemperature(), response.getBody().getCurrentTemperature());
        assertEquals(testBuilding.getTargetTemperature(), response.getBody().getTargetTemperature());

        verify(buildingService).createBuilding(any(BuildingRequest.class));
    }

    /**
     * Test retrieving an existing building by ID.
     * Verifies that:
     * 1. The controller returns HTTP 200 OK
     * 2. The response body contains the correct building
     * 3. The building service's getBuilding method is called
     */
    @Test
    void getBuilding_Success() {
        when(buildingService.getBuilding(1L)).thenReturn(testBuilding);

        ResponseEntity<Building> response = buildingController.getBuilding(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testBuilding.getId(), response.getBody().getId());

        verify(buildingService).getBuilding(1L);
    }

    /**
     * Test retrieving a non-existent building.
     * Verifies that:
     * 1. The controller returns HTTP 404 NOT FOUND
     * 2. The response body is null
     * 3. The building service's getBuilding method is called
     */
    @Test
    void getBuilding_NotFound() {
        when(buildingService.getBuilding(999L)).thenReturn(null);

        ResponseEntity<Building> response = buildingController.getBuilding(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(buildingService).getBuilding(999L);
    }

    /**
     * Test retrieving all buildings.
     * Verifies that:
     * 1. The controller returns HTTP 200 OK
     * 2. The response body contains the list of buildings
     * 3. The building service's getAllBuildings method is called
     */
    @Test
    void getAllBuildings_Success() {
        List<Building> buildings = Arrays.asList(testBuilding);
        when(buildingService.getAllBuildings()).thenReturn(buildings);

        ResponseEntity<List<Building>> response = buildingController.getAllBuildings();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(testBuilding.getId(), response.getBody().get(0).getId());

        verify(buildingService).getAllBuildings();
    }

    /**
     * Test updating a building's target temperature.
     * Verifies that:
     * 1. The controller returns HTTP 200 OK
     * 2. The response body contains the updated building
     * 3. The building service's updateTemperature method is called
     */
    @Test
    void updateTemperature_Success() {
        when(buildingService.updateTemperature(1L, 25.0)).thenReturn(testBuilding);

        ResponseEntity<Building> response = buildingController.updateTemperature(1L, 25.0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testBuilding.getId(), response.getBody().getId());

        verify(buildingService).updateTemperature(1L, 25.0);
    }

    /**
     * Test updating temperature for a non-existent building.
     * Verifies that:
     * 1. The controller returns HTTP 404 NOT FOUND
     * 2. The response body is null
     * 3. The building service's updateTemperature method is called
     */
    @Test
    void updateTemperature_NotFound() {
        when(buildingService.updateTemperature(999L, 25.0)).thenReturn(null);

        ResponseEntity<Building> response = buildingController.updateTemperature(999L, 25.0);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(buildingService).updateTemperature(999L, 25.0);
    }

    /**
     * Test deleting a building.
     * Verifies that:
     * 1. The controller returns HTTP 200 OK
     * 2. The building service's deleteBuilding method is called
     */
    @Test
    void deleteBuilding_Success() {
        doNothing().when(buildingService).deleteBuilding(1L);

        ResponseEntity<Void> response = buildingController.deleteBuilding(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(buildingService).deleteBuilding(1L);
    }
}
