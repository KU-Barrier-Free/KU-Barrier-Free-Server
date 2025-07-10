package com.example.BarrierKU.domain.indoor;

import com.example.BarrierKU.domain.type.Purpose;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
