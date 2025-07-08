package com.example.BarrierKU.domain.Indoor;

import com.example.BarrierKU.domain.Image.Image;
import com.example.BarrierKU.domain.Type.RoomType;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Room {

    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private String roomName;

    @Column(nullable = false)
    private boolean lecture; // 수업 여부

    @Column(nullable = false)
    private String floor;

    @Column(nullable = false)
    private Long capacity;

    @Column(nullable = false)
    private float area;

    private String roomComment;

    @Column(nullable = false)
    private float floorSpace; // 평수

    @Column(nullable = false)
    private RoomType roomType; // 평탄식, 계단식

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String departmentNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_info_id")
    private RoomInfo roomInfo;

    @OneToMany(mappedBy = "room")
    private List<Image> images = new ArrayList<>();

}
