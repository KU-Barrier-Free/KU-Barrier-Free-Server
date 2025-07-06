package com.example.BarrierKU.dto;

import com.example.BarrierKU.domain.Indoor.Room;
import com.example.BarrierKU.domain.Type.RoomType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SpaceResponse {
    private Long spaceId;
    private String roomNumber;
    private String roomName;
    private Boolean lecture;
//    private String floor;
    private Long capacity;
    private Float area;
    private String roomComment;
    private Float floorSpace;
    private String roomType;
    private String department;
    private String departmentNumber;
    private RoomInfoResponse roomInfo;
    private List<ImageResponse> images;

    public static SpaceResponse of(Room room, int type) {
        RoomInfoResponse info = (type == 1 && room.getRoomInfo() != null)
                ? RoomInfoResponse.from(room.getRoomInfo()) : null;

        List<ImageResponse> images = (type == 1)
                ? room.getImages().stream().map(ImageResponse::from).toList() : List.of();

        return SpaceResponse.builder()
                .spaceId(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomName(room.getRoomName())
                .lecture(room.isLecture())
//                .floor(room.getFloor())
                .capacity(room.getCapacity())
                .area(room.getArea())
                .roomComment(room.getRoomComment())
                .floorSpace(room.getFloorSpace())
                .roomType(room.getRoomType().name())
                .department(room.getDepartment())
                .departmentNumber(room.getDepartmentNumber())
                .roomInfo(info)
                .images(images)
                .build();
    }
}

