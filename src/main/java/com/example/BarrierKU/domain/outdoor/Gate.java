package com.example.BarrierKU.domain.outdoor;

import jakarta.persistence.*;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
public class Gate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gate_id")
    private Long id;

    private String name;

    @Column(nullable = false)
    private Point spot;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageUrl;
}

