package com.example.BarrierKU.domain.Image;

import com.example.BarrierKU.domain.Outdoor.OutsideSignificant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Entity
@Getter
public class OutsideSignificantImage {

    @Id @GeneratedValue
    @Column(name = "outside_siginificant_image_id")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outside_significant_id", nullable = false)
    private OutsideSignificant outsideSignificant;

}
