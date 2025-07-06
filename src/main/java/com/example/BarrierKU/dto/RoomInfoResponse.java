package com.example.BarrierKU.dto;

import com.example.BarrierKU.domain.Indoor.RoomInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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
