package com.example.BarrierKU.domain.door.repository;

import com.example.BarrierKU.domain.indoor.Door;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoorRepository extends JpaRepository<Door, Long> {
}
