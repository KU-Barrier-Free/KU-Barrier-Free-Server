package com.example.BarrierKU.domain.Indoor;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class RoomInfo {

    @Id @GeneratedValue
    @Column(name = "room_info_id")
    private Long id;

    @Column(nullable = false)
    private int allInOne;
    @Column(nullable = false)
    private int cinemaSeat;
    @Column(name = "one_seat", nullable = false)
    private int single; //oneSeat를 임의로 변경
    @Column(name = "two_seat",nullable = false)
    private int couple;
    @Column(name = "multi_seat",nullable = false)
    private int multiple;
    @Column(nullable = false)
    private int sneezeGuard; // 왜...?
    @Column(name = "back_of_chair",nullable = false)
    private int backChair; // 백 오브 체어를 임의로 변경
    @Column(nullable = false)
    private int wheelchairTable;
    @Column(nullable = false)
    private int wheelChair;
    @Column(nullable = false)
    private int computerTable;
    @Column(nullable = false)
    private int frontDoor;
    @Column(nullable = false)
    private int backDoor;

    @OneToOne(mappedBy = "roomInfo", fetch = FetchType.LAZY)
    private Room room;
}
