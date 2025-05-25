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

    private int allInOne;
    private int cinemaSeat;
    private int single; //oneSeat를 임의로 변경
    private int couple;
    private int multiple;
    private int sneezeGuard; // 왜...?
    private int backChair; // 백 오브 체어를 임의로 변경
    private int wheelchairTable;
    private int wheelChair;
    private int computerTable;
    private int frontDoor;
    private int backDoor;

    @OneToOne(mappedBy = "roomInfo", fetch = FetchType.LAZY)
    private Room room;
}
