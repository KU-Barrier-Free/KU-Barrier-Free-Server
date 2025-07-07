package com.example.BarrierKU.dto;

import com.example.BarrierKU.domain.Indoor.Room;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SpaceResponse {
    private String roomNumber;
    private String roomName;
    private Boolean lecture;
    private Long capacity;
    private Float area;
    private String roomComment;
    private Float floorSpace;
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

        return SpaceResponse.builder()
                .roomNumber(room.getRoomNumber())
                .roomName(room.getRoomName())
                .lecture(room.isLecture())
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

