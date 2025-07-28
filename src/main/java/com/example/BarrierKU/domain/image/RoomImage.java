package com.example.BarrierKU.domain.image;

import com.example.BarrierKU.domain.indoor.Room;
import com.example.BarrierKU.domain.type.ImageType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class RoomImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_image_id")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String url;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",nullable = false)
    private Room room;
}
