package com.example.BarrierKU.domain.Indoor;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Significant {

    @Id @GeneratedValue
    @Column(name = "significant_id")
    private Long id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;
}
