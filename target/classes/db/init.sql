CREATE DATABASE IF NOT EXISTS building_temp_control;
USE building_temp_control;

CREATE TABLE IF NOT EXISTS building (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(200) NOT NULL,
    current_temperature DOUBLE NOT NULL,
    target_temperature DOUBLE NOT NULL,
    status VARCHAR(20) NOT NULL,
    last_updated DATETIME NOT NULL,
    create_time DATETIME NOT NULL
);

-- Insert sample data
INSERT INTO building (name, location, current_temperature, target_temperature, status, last_updated, create_time)
VALUES 
('Office Building A', 'New York', 22.5, 23.0, 'HEATING', NOW(), NOW()),
('Shopping Mall B', 'Los Angeles', 24.8, 24.0, 'COOLING', NOW(), NOW()),
('Residential Complex C', 'Chicago', 21.0, 21.0, 'MAINTAINING', NOW(), NOW()); 