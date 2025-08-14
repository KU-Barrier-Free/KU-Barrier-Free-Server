package com.example.BarrierKU.domain.indoor;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RoomInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_info_id")
    private Long id;

    @Column(nullable = false)
    private boolean allInOne;
    @Column(nullable = false)
    private boolean cinemaSeat;
    @Column(name = "one_seat", nullable = false)
    private boolean oneSeat;
    @Column(name = "two_seat",nullable = false)
    private boolean twoSeat;
    @Column(name = "multi_seat",nullable = false)
    private boolean multiSeat;
    @Column(nullable = false)
    private boolean panel;
    @Column(name = "back_of_chair",nullable = false)
    private boolean backOfChair;
    @Column(nullable = false)
    private boolean wheelchairTable;
    @Column(nullable = false)
    private boolean wheelChair;
    @Column(nullable = false)
    private boolean computerTable;
    @Column(nullable = false)
    private boolean frontDoor;
    @Column(nullable = false)
    private boolean backDoor;
    @Column(nullable = false)
    private boolean ramp;
    @Column(nullable = false)
    private boolean stair;
    private String roomComment;

    @OneToOne(mappedBy = "roomInfo", fetch = FetchType.LAZY)
    private Room room;
}
