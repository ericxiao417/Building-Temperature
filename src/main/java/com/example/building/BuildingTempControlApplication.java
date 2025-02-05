package com.example.building;

// Importing necessary Spring Boot and MyBatis libraries
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application class for the Building Temperature Control System.
 * This class is responsible for bootstrapping the Spring Boot application.
 */
@SpringBootApplication // Marks this as a Spring Boot application
@MapperScan("com.example.building.mapper") // Scans the specified package for MyBatis mapper interfaces
public class BuildingTempControlApplication {
    public static void main(String[] args) {
        // Run the Spring Boot application
        SpringApplication.run(BuildingTempControlApplication.class, args);
    }
} 