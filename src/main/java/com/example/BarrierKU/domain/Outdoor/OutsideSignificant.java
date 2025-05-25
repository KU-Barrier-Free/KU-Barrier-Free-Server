package com.example.BarrierKU.domain.Outdoor;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class OutsideSignificant {

    @Id @Generated
    @Column(name = "outside_significant_id")
    private Long id;

    @Column(nullable = false)
    private Point spot;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "outsideSignificant")
    private List<OutsideSignificant> outsideSignificants = new ArrayList<>();
}
