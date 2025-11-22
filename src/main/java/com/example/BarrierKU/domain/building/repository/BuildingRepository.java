package com.example.BarrierKU.domain.building.repository;

import com.example.BarrierKU.domain.indoor.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    @Query("""
        SELECT b FROM Building b
        WHERE REPLACE(LOWER(CONCAT(b.name, ',', COALESCE(b.synonyms, ''))), ' ', '')
        LIKE CONCAT('%', :name, '%')
    """)
    List<Building> findByNameOrSynonyms(String name);
}
