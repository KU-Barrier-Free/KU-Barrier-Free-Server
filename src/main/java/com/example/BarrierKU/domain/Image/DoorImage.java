package com.example.BarrierKU.domain.Image;

import com.example.BarrierKU.domain.Indoor.Door;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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
