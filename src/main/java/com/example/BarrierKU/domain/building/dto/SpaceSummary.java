package com.example.BarrierKU.domain.building.dto;

import com.example.BarrierKU.domain.indoor.Room;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SpaceSummary (
        @Schema(description = "공간 ID", example = "1")
        Long id,
        @Schema(description = "호수", example = "201")
        String roomNumber,
        @Schema(description = "호실명", example = "대강의실")
        String roomName,
        @Schema(description = "특이사항", example = "턱 있음")
        String comment,
        @Schema(description = "수업 여부", example = "true")
        boolean isLecture,
        List<RoomImageResponse> roomImages
) {
        public static SpaceSummary from(Room room) {
                return new SpaceSummary (
                        room.getId(),
                        room.getRoomNumber(),
                        room.getRoomName(),
                        room.getRoomComment(),
                        room.isLecture(),
                        room.getRoomImages().stream()
                                .map(RoomImageResponse::from)
                                .toList()
                );
        }
}
