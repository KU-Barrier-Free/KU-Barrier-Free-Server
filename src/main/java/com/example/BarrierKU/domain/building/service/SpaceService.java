package com.example.BarrierKU.domain.building.service;

import com.example.BarrierKU.common.exception.BarrierKuException;
import com.example.BarrierKU.domain.indoor.Room;
import com.example.BarrierKU.domain.building.dto.SpaceResponse;
import com.example.BarrierKU.domain.building.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.BarrierKU.common.response.ResponseCode.SPACE_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpaceService {

    private final RoomRepository roomRepository;

    public SpaceResponse getSpaceInfo(Long buildingId, Long spaceId, int type) {
        Room room = roomRepository.findByIdAndBuildingId(spaceId, buildingId)
                .orElseThrow(() -> new BarrierKuException(SPACE_NOT_FOUND));
        return SpaceResponse.of(room, type);
    }
}
