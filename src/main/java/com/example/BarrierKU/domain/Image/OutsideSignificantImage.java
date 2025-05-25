package com.example.BarrierKU.domain.Image;

import com.example.BarrierKU.domain.Outdoor.OutsideSignificant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class OutsideSignificantImage {

    @Id @GeneratedValue
    @Column(name = "outside_siginificant_image_id")
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outside_significant_id")
    private OutsideSignificant outsideSignificants;

}
