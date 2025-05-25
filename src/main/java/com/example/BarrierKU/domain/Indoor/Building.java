package com.example.BarrierKU.domain.Indoor;

import com.example.BarrierKU.domain.Type.Purpose;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
public class Building {
    @Id @GeneratedValue
    @Column(name = "building_id")
    private Long id;

    private int number; // 건물 번호

    private String name;

    private String department;

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

    @Transient
    public Set<Purpose> getFacilityPurposes() {
        if (facilities == null) return Collections.emptySet();

        return facilities.stream()
                .map(Facilities::getPurpose)
                .collect(Collectors.toSet());
    }

}
