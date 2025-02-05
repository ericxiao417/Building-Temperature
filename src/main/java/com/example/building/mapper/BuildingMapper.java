package com.example.building.mapper;

import com.example.building.model.Building;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BuildingMapper {
    @Insert("INSERT INTO building (name, location, current_temperature, target_temperature, status, last_updated, create_time) " +
            "VALUES (#{name}, #{location}, #{currentTemperature}, #{targetTemperature}, #{status}, #{lastUpdated}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Building building);

    @Select("SELECT * FROM building WHERE id = #{id}")
    Building findById(Long id);

    @Select("SELECT * FROM building")
    List<Building> findAll();

    @Update("UPDATE building SET current_temperature = #{currentTemperature}, " +
            "target_temperature = #{targetTemperature}, status = #{status}, " +
            "last_updated = #{lastUpdated} WHERE id = #{id}")
    int update(Building building);

    @Delete("DELETE FROM building WHERE id = #{id}")
    int deleteById(Long id);
} 