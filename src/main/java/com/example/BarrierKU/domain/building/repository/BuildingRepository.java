package com.example.BarrierKU.domain.building.repository;

import com.example.BarrierKU.domain.indoor.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    List<Building> findByNameContainingIgnoreCase(String name);
}
