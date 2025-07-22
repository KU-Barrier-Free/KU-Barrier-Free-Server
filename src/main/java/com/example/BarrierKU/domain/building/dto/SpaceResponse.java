package com.example.BarrierKU.domain.building.dto;

import com.example.BarrierKU.domain.indoor.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SpaceResponse {
    @Schema(description = "호수", example = "201호")
    private String roomNumber;
    @Schema(description = "호실명", example = "대강의실")
    private String roomName;
    @Schema(description = "수업 여부", example = "true")
    private boolean lecture;
    @Schema(description = "수용인원", example = "165")
    private long capacity;
    @Schema(description = "호실면적 (m²)", example = "190.4")
    private float area;
    @Schema(description = "특이사항", example = "턱 있음")
    private String roomComment;
    @Schema(description = "평 수", example = "57.6")
    private float floorSpace;
    @Schema(description = "호실형태", example = "계단식")
    private String roomType;
    @Schema(description = "관리부서", example = "경영학과")
    private String department;
    @Schema(description = "관리부서 전화번호", example = "02-450-3628")
    private String departmentNumber;
    @Schema(description = "강의실 조사 정보")
    private RoomInfoResponse roomInfo;
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
    private List<RoomImageResponse> images;

    public static SpaceResponse of(Room room, int type) {
        RoomInfoResponse info = (type == 1 && room.getRoomInfo() != null)
                ? RoomInfoResponse.from(room.getRoomInfo()) : null;

        List<RoomImageResponse> images = room.getRoomImages().stream().map(RoomImageResponse::from).toList();

        return new SpaceResponse (
                room.getRoomNumber() + "호",
                room.getRoomName(),
                room.isLecture(),
                room.getCapacity(),
                room.getArea(),
                room.getRoomComment(),
                room.getFloorSpace(),
                room.getRoomType().getValue(),
                room.getDepartment(),
                room.getDepartmentNumber(),
                info,
                images
        );

    }
}

