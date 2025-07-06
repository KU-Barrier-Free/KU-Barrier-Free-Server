package com.example.BarrierKU.dto;

import com.example.BarrierKU.domain.Indoor.RoomInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomInfoResponse {
    private boolean allInOne;
    private boolean cinemaSeat;
    private boolean oneSeat;
    private boolean twoSeat;
    private boolean multiSeat;
    private boolean panel;
    private boolean backOfChair;
    private boolean wheelchairTable;
    private boolean wheelChair;
    private boolean computerTable;
    private boolean frontDoor;
    private boolean backDoor;

    public static RoomInfoResponse from(RoomInfo info) {
        return RoomInfoResponse.builder()
                .allInOne(info.isAllInOne())
                .cinemaSeat(info.isCinemaSeat())
                .oneSeat(info.isOneSeat())
                .twoSeat(info.isTwoSeat())
                .multiSeat(info.isMultiSeat())
                .panel(info.isPanel())
                .backOfChair(info.isBackOfChair())
                .wheelchairTable(info.isWheelchairTable())
                .wheelChair(info.isWheelChair())
                .computerTable(info.isComputerTable())
                .frontDoor(info.isFrontDoor())
                .backDoor(info.isBackDoor())
                .build();
    }
}
