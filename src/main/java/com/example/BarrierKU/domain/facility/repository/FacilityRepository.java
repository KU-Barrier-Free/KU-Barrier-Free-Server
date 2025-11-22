package com.example.BarrierKU.domain.facility.repository;

import com.example.BarrierKU.domain.indoor.Facilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facilities, Long> {

    @Query("SELECT f FROM Facilities f JOIN FETCH f.building WHERE f.id = :id")
    Optional<Facilities> findWithBuildingById(@Param("id") Long id);

    @Query("""
        SELECT DISTINCT f FROM Facilities f
        JOIN FETCH f.building b
        LEFT JOIN FacilitySynonym syn
            ON f.name = syn.baseName
        WHERE
            REPLACE(LOWER(f.name), ' ', '') LIKE CONCAT('%', :searchWord, '%')
            OR REPLACE(LOWER(syn.synonym), ' ', '') LIKE CONCAT('%', :searchWord, '%')
    """)
    List<Facilities> findByNameOrSynonym(String searchWord);

    List<Facilities> findAllByBuildingId(Long buildingId);
}
