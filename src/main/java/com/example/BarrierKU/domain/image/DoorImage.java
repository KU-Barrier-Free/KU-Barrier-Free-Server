package com.example.BarrierKU.domain.image;

import com.example.BarrierKU.domain.indoor.Door;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class DoorImage {

    @Id @GeneratedValue
    @Column(name = "door_image_id")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "door_id", nullable = false)
    private Door door;
}
