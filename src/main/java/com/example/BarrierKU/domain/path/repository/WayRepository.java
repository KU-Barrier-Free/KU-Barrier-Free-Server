package com.example.BarrierKU.domain.path.repository;

import com.example.BarrierKU.domain.path.model.Way;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WayRepository extends JpaRepository<Way, Long> {
}