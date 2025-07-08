package com.example.BarrierKU.domain.Indoor;

import com.example.BarrierKU.domain.Type.Purpose;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class Facilities {

    @Id @GeneratedValue
    @Column(name = "facilities_id")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose", nullable = false)
    private Purpose purpose;

    @Column(nullable = false)
    @NotNull
    private String floor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;
}
