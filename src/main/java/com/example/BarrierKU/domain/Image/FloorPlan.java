package com.example.BarrierKU.domain.Image;

import com.example.BarrierKU.domain.Indoor.Building;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Entity
@Getter
public class FloorPlan {

    @Id
    @GeneratedValue
    @Column(name = "floor_plan_id")
    private long id;

    @NotNull
    private String image;

    @NotNull
    private String floor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    Building building;

}
