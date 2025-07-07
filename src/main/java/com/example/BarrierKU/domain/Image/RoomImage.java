package com.example.BarrierKU.domain.Image;

import com.example.BarrierKU.domain.Indoor.Room;
import com.example.BarrierKU.domain.Type.ImageType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class RoomImage {

    @Id @GeneratedValue
    @Column(name = "room_image_id")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String url;

    private ImageType imageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",nullable = false)
    private Room room;
}
