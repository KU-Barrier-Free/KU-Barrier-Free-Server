package com.example.BarrierKU.domain.Indoor;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@Getter @Setter
public class Door {

    @Id @GeneratedValue
    @Column(name = "door_id")
    private Long id;

//    @Column(nullable = false)
//    private Point spot;

    @Column(nullable = false)
    private int wheelchair;

    private String significant;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;
}
