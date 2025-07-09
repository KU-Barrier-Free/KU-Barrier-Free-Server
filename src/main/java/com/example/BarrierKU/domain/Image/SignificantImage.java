package com.example.BarrierKU.domain.Image;

import com.example.BarrierKU.domain.Indoor.Significant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Entity
@Getter
public class SignificantImage {

    @Id @GeneratedValue
    @Column(name = "significant_image_id")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "significant_id")
    private Significant significant;
}
