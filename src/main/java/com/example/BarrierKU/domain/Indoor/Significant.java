package com.example.BarrierKU.domain.Indoor;

import com.example.BarrierKU.domain.Image.SignificantImage;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "significant")
    private List<SignificantImage> images = new ArrayList<>();
}
