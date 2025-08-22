package com.example.BarrierKU.domain.path.repository;

import com.example.BarrierKU.domain.path.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface NodeRepository extends JpaRepository<Node, Long> {

    // 위경도로부터 가장 가까운 노드 찾기 (거리 단위: meter)
    @Query(value = """
        SELECT * 
        FROM node 
        ORDER BY ST_DistanceSphere(location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)) 
        LIMIT 1
        """, nativeQuery = true)
    Optional<Node> findNearestNode(@Param("lat") double lat, @Param("lon") double lon);
}


