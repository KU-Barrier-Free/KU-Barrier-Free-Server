package com.example.BarrierKU.domain.Image;

import com.example.BarrierKU.domain.Indoor.Room;
import com.example.BarrierKU.domain.Type.ImageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Image {

    @Id @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String url;

    private ImageType imageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",nullable = false)
    private Room room;
}
