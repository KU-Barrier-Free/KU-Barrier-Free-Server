package com.example.BarrierKU.domain.image;

import com.example.BarrierKU.domain.indoor.Building;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Entity
@Getter
public class Drawing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "floor_drawing_id")
    private long id;

    @NotNull
    private String image;

    @NotNull
    private String floor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    Building building;

}
