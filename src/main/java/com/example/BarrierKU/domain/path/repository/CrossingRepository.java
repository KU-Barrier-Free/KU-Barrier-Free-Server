package com.example.BarrierKU.domain.path.repository;

import com.example.BarrierKU.domain.path.model.Crossing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrossingRepository extends JpaRepository<Crossing, Long> {
}
