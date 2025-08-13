package com.example.BarrierKU.domain.indoor;

import com.example.BarrierKU.domain.image.SignificantImage;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Significant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "significant_id")
    private Long id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @OneToMany(mappedBy = "significant")
    private List<SignificantImage> images = new ArrayList<>();
}
