package com.example.BarrierKU.domain.facility.repository;

import com.example.BarrierKU.domain.indoor.FacilitySynonym;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilitySynonymRepository extends JpaRepository<FacilitySynonym, Long> {

    List<FacilitySynonym> findBySynonymContainingIgnoreCase(String keyword);
}
