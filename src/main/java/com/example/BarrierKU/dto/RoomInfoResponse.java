package com.example.BarrierKU.dto;

import com.example.BarrierKU.domain.Indoor.RoomInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomInfoResponse {
    private Boolean allInOne;
    private Boolean cinemaSeat;
    private Boolean oneSeat;
    private Boolean twoSeat;
    private Boolean multiSeat;
    private Boolean panel;
    private Boolean backOfChair;
    private Boolean wheelchairTable;
    private Boolean wheelChair;
    private Boolean computerTable;
    private Boolean frontDoor;
    private Boolean backDoor;

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
