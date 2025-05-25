package com.example.BarrierKU.domain.Image;

import com.example.BarrierKU.domain.Indoor.Significant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class SignificantImage {

    @Id @GeneratedValue
    @Column(name = "significant_image_id")
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "significant_id")
    private Significant significant;
}
