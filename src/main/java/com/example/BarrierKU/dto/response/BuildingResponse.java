package com.example.BarrierKU.dto.response;

import com.example.BarrierKU.domain.Indoor.Building;
import com.example.BarrierKU.domain.Indoor.Door;
import com.example.BarrierKU.domain.Type.Purpose;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class BuildingResponse {
    private Long id;

    private int number;

    private String name;

    private String department;

    private String image;

    private List<DoorResponse> doors;

    private Set<Purpose> FacilityPurposes;

    public BuildingResponse from(Building building){
        return new BuildingResponse(building.getId(), building.getNumber(),
                building.getName(), building.getDepartment(),
                building.getImage(),
                building.getDoors().stream()
                .map(DoorResponse::from)
                .toList(),
                building.getFacilityPurposes());
    }
}
