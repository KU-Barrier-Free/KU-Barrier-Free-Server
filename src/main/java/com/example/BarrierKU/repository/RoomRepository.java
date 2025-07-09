package com.example.BarrierKU.repository;

import com.example.BarrierKU.domain.Indoor.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.id = :id AND r.building.id = :buildingId")
    Optional<Room> findByIdAndBuildingId(@Param("id") Long id, @Param("buildingId") Long buildingId);
}
