package com.example.BarrierKU.domain.indoor;

import com.example.BarrierKU.domain.image.FloorPlan;
import com.example.BarrierKU.domain.type.Purpose;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter

public class Building {
    @Id
    @GeneratedValue
    @Column(name = "building_id")
    private Long id;

    private int number; // 건물 번호

    @Column(nullable = false)
    @NotNull
    private String name;

    private String department;

    @Column(nullable = false)
    @NotNull
    private String image; // 건물 사진...?

    @Column(nullable = false)
    private Point spot;

    @OneToMany(mappedBy = "building")
    private List<Door> doors = new ArrayList<>();

    @OneToMany(mappedBy = "building")
    private List<Significant> significants = new ArrayList<>();

    @OneToMany(mappedBy = "building")
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "building")
    private List<Facilities> facilities = new ArrayList<>();

    @OneToMany(mappedBy = "building")
    private List<FloorPlan> floorPlans = new ArrayList<>();

    @Transient
    public Set<Purpose> getFacilityPurposes() {
        if (facilities == null) return Collections.emptySet();

        return facilities.stream()
                .map(Facilities::getPurpose)
                .collect(Collectors.toSet());
    }

}
