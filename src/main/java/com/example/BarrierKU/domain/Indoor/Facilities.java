package com.example.BarrierKU.domain.Indoor;

import com.example.BarrierKU.domain.Type.Purpose;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Facilities {

    @Id @GeneratedValue
    @Column(name = "facilities_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose", nullable = false)
    private Purpose purpose;

    private String floor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;
}
