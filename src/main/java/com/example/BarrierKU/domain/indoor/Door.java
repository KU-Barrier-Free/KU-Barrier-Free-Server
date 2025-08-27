package com.example.BarrierKU.domain.indoor;

import com.example.BarrierKU.domain.image.DoorImage;
import jakarta.persistence.*;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Door {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "door_id")
    private Long id;

    @Column(nullable = false)
    private Point spot;

    @Column(nullable = false)
    private boolean wheelchair;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @OneToMany(mappedBy = "door")
    private List<DoorImage> images = new ArrayList<>();
}
