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
    private boolean allInOne;
    @Column(nullable = false)
    private boolean cinemaSeat;
    @Column(name = "one_seat", nullable = false)
    private boolean oneSeat; //oneSeat를 임의로 변경
    @Column(name = "two_seat",nullable = false)
    private boolean twoSeat;
    @Column(name = "multi_seat",nullable = false)
    private boolean multiSeat;
    @Column(nullable = false)
    private boolean panel; // 왜...?
    @Column(name = "back_of_chair",nullable = false)
    private boolean backOfChair; // 백 오브 체어를 임의로 변경
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

    @OneToOne(mappedBy = "roomInfo", fetch = FetchType.LAZY)
    private Room room;
}
