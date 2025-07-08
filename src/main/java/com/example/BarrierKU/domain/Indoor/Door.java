package com.example.BarrierKU.domain.Indoor;

import com.example.BarrierKU.domain.Image.DoorImage;
import jakarta.persistence.*;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Door {

    @Id @GeneratedValue
    @Column(name = "door_id")
    private Long id;

    @Column(nullable = false)
    private Point spot;

    @Column(nullable = false)
    private boolean wheelchair;

    private String significant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @OneToMany(mappedBy = "door")
    private List<DoorImage> images = new ArrayList<>();
}
