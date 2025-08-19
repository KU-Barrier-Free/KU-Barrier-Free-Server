package com.example.BarrierKU.domain.building.dto;

import com.example.BarrierKU.domain.indoor.Room;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SpaceSummary(
        @Schema(description = "공간 ID", example = "1")
        Long id,
        @Schema(description = "호수", example = "201호")
        String roomNumber,
        @Schema(description = "호실명", example = "대강의실")
        String roomName,
        @Schema(description = "특이사항", example = "턱 있음")
        String comment,
        @Schema(description = "강의실 사진 정보",
                example = """
                        [
                          {
                            "imageUrl": "https://example.com/room1.png",
                            "imageType": "ROOM"
                          },
                          {
                            "imageUrl": "https://example.com/room2.png",
                            "imageType": "DOOR"
                          }
                        ]
                        """
        )
        List<RoomImageResponse> roomImages,
        @Schema(description = "강의여부", example = "true")
        boolean isLecture
) {
    public static SpaceSummary from(Room room) {
        String comment = "";
        if(room.getRoomInfo() != null) {
            if(room.getRoomInfo().isRamp()) comment += "경사로 있음 ";
            if(room.getRoomInfo().getRoomComment() != null) comment += room.getRoomInfo().getRoomComment();
        }

        return new SpaceSummary(
                room.getId(),
                room.getRoomNumber() + "호",
                room.getRoomName(),
                comment,
                room.getRoomImages().stream()
                        .map(RoomImageResponse::from)
                        .toList(),
                room.isLecture()
        );
    }
}
