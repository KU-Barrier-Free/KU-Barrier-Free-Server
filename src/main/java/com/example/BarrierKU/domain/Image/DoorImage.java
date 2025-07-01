package com.example.BarrierKU.domain.Image;

import com.example.BarrierKU.domain.Indoor.Door;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class DoorImage {

    @Id @GeneratedValue
    @Column(name = "door_image_id")
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "door_id", nullable = false)
    private Door door;
}
