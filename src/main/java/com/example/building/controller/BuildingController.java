package com.example.building.controller;

import com.example.building.dto.BuildingRequest;
import com.example.building.model.Building;
import com.example.building.service.BuildingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/buildings")
@Api(tags = "Building Temperature Control API")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @PostMapping
    @ApiOperation("Create a new building")
    public ResponseEntity<Building> createBuilding(@Valid @RequestBody BuildingRequest request) {
        return ResponseEntity.ok(buildingService.createBuilding(request));
    }

    @GetMapping("/{id}")
    @ApiOperation("Get building by ID")
    public ResponseEntity<Building> getBuilding(@PathVariable Long id) {
        Building building = buildingService.getBuilding(id);
        return building != null ? ResponseEntity.ok(building) : ResponseEntity.notFound().build();
    }

    @GetMapping
    @ApiOperation("Get all buildings")
    public ResponseEntity<List<Building>> getAllBuildings() {
        return ResponseEntity.ok(buildingService.getAllBuildings());
    }

    @PutMapping("/{id}/temperature")
    @ApiOperation("Update building target temperature")
    public ResponseEntity<Building> updateTemperature(
            @PathVariable Long id,
            @RequestParam Double targetTemperature) {
        Building building = buildingService.updateTemperature(id, targetTemperature);
        return building != null ? ResponseEntity.ok(building) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete building")
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
        buildingService.deleteBuilding(id);
        return ResponseEntity.ok().build();
    }
} 