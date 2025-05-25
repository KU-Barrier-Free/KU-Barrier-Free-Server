package com.example.BarrierKU.domain.Indoor;

import com.example.BarrierKU.domain.Image.Image;
import com.example.BarrierKU.domain.Type.RoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Room {

    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    private String roomNumber;

    private String roomName;

    private int lecture; // 수업 여부

    private String floor;

    private Long capacity;

    private float area;

    private String roomComment;

    private float floorSpace; // 평수

    private RoomType roomType; // 평탄식, 계단식

    private String department;

    private String departmentNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_info_id")
    private RoomInfo roomInfo;

    @OneToMany(mappedBy = "room")
    private List<Image> images = new ArrayList<>();

}
