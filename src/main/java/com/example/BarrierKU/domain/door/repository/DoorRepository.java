package com.example.BarrierKU.domain.door.repository;

import com.example.BarrierKU.domain.indoor.Door;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DoorRepository extends JpaRepository<Door, Long> {

    @Query(value = """
            SELECT d.spot
            FROM door d
            WHERE d.building_id = :buildingId
            ORDER BY ST_DistanceSphere(d.spot, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326))
            LIMIT 1
            """, nativeQuery = true)
    Optional<Point> findNearestDoorSpot(@Param("buildingId") Long buildingId,
                                        @Param("lat") double lat,
                                        @Param("lon") double lon);


    @Query(value = """
            SELECT d.spot
            FROM door d
            WHERE d.building_id = :buildingId
              AND d.wheelchair = true
            ORDER BY ST_DistanceSphere(d.spot, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326))
            LIMIT 1
            """, nativeQuery = true)
    Optional<Point> findNearestWheelchairDoorSpot(@Param("buildingId") Long buildingId,
                                                  @Param("lat") double lat,
                                                  @Param("lon") double lon);


}
