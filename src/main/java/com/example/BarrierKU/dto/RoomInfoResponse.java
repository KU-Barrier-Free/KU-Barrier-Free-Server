package com.example.BarrierKU.dto;

import com.example.BarrierKU.domain.Indoor.RoomInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
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
        return new RoomInfoResponse(
                info.isAllInOne(),
                info.isCinemaSeat(),
                info.isOneSeat(),
                info.isTwoSeat(),
                info.isMultiSeat(),
                info.isPanel(),
                info.isBackOfChair(),
                info.isWheelchairTable(),
                info.isWheelChair(),
                info.isComputerTable(),
                info.isFrontDoor(),
                info.isBackDoor());
    }
}
