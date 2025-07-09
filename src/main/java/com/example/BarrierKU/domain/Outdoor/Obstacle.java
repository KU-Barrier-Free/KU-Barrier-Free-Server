package com.example.BarrierKU.domain.Outdoor;

import com.example.BarrierKU.domain.Type.ObstacleType;
import jakarta.persistence.*;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
public class Obstacle {

    @Id @GeneratedValue
    @Column(name = "obstacle_id")
    private Long id;

    @Column(nullable = false)
    private Point spot;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ObstacleType obstacleType;
}

