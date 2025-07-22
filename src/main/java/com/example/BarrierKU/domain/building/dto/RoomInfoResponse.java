package com.example.BarrierKU.domain.building.dto;

import com.example.BarrierKU.domain.indoor.RoomInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomInfoResponse {
    @Schema(description = "일체형 책상", example = "false")
    private boolean allInOne;
    @Schema(description = "영화관 의자", example = "true")
    private boolean cinemaSeat;
    @Schema(description = "1인용 책상 및 의자", example = "true")
    private boolean oneSeat;
    @Schema(description = "2인용 책상 및 의자", example = "false")
    private boolean twoSeat;
    @Schema(description = "다인용 책상 및 의자", example = "false")
    private boolean multiSeat;
    @Schema(description = "다리 가리개", example = "false")
    private boolean panel;
    @Schema(description = "등받이", example = "true")
    private boolean backOfChair;
    @Schema(description = "휠체어용 책상", example = "false")
    private boolean wheelchairTable;
    @Schema(description = "바퀴 의자", example = "false")
    private boolean wheelChair;
    @Schema(description = "컴퓨터 책상", example = "false")
    private boolean computerTable;
    @Schema(description = "앞문", example = "true")
    private boolean frontDoor;
    @Schema(description = "뒷문", example = "true")
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
