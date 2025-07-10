package com.example.BarrierKU.domain.building.dto;

import com.example.BarrierKU.domain.Indoor.Building;
import com.example.BarrierKU.domain.Indoor.Significant;
import com.example.BarrierKU.domain.Type.Purpose;
import com.example.BarrierKU.domain.door.dto.DoorResponse;
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

    private List<Significant> significants;

    public static BuildingResponse from(Building building){
        return new BuildingResponse(building.getId(), building.getNumber(),
                building.getName(), building.getDepartment(),
                building.getImage(),
                building.getDoors().stream()
                .map(DoorResponse::from)
                .toList(),
                building.getFacilityPurposes(), building.getSignificants());
    }
}
