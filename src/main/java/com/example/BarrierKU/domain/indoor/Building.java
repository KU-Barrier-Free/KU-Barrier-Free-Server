package com.example.BarrierKU.domain.indoor;

import com.example.BarrierKU.domain.image.Drawing;

import com.example.BarrierKU.domain.type.Purpose;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Building {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id")
    private Long id;

    private Integer number; // 건물 번호

    @Column(nullable = false)
    @NotNull
    private String name;

    private String department;

    @Column(nullable = false)
    @NotNull
    private String image; // 건물 사진

    @Column(nullable = false)
    private Point spot;

    @Column(nullable = false)
    @NotNull
    private boolean lecture;

    @OneToMany(mappedBy = "building")
    private List<Door> doors = new ArrayList<>();

    @OneToMany(mappedBy = "building")
    private List<Significant> significants = new ArrayList<>();

    @OneToMany(mappedBy = "building")
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "building")
    private List<Facilities> facilities = new ArrayList<>();

    @OneToMany(mappedBy = "building")
    private List<Drawing> drawings = new ArrayList<>();

    @Transient
    public Set<Purpose> getFacilityPurposes() {
        if (facilities == null) return Collections.emptySet();

        return facilities.stream()
                .map(Facilities::getPurpose)
                .collect(Collectors.toSet());
    }

    public Building(Integer number, String name, Point spot, boolean lecture) {
        this.number = number;
        this.name = name;
        this.spot = spot;
        this.lecture = lecture;
        this.image = "";
    }
}
