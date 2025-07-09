package com.example.BarrierKU.dto;

import com.example.BarrierKU.domain.Indoor.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SpaceResponse {
    private String roomNumber;
    private String roomName;
    private boolean lecture;
    private long capacity;
    private float area;
    private String roomComment;
    private float floorSpace;
    private String roomType;
    private String department;
    private String departmentNumber;
    private RoomInfoResponse roomInfo;
    private List<RoomImageResponse> images;

    public static SpaceResponse of(Room room, int type) {
        RoomInfoResponse info = (type == 1 && room.getRoomInfo() != null)
                ? RoomInfoResponse.from(room.getRoomInfo()) : null;

        List<RoomImageResponse> images = (type == 1)
                ? room.getRoomImages().stream().map(RoomImageResponse::from).toList() : List.of();

        return new SpaceResponse (
                room.getRoomNumber(),
                room.getRoomName(),
                room.isLecture(),
                room.getCapacity(),
                room.getArea(),
                room.getRoomComment(),
                room.getFloorSpace(),
                room.getRoomType().name(),
                room.getDepartment(),
                room.getDepartmentNumber(),
                info,
                images
        );

    }
}

