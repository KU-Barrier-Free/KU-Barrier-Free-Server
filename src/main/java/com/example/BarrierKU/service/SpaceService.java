package com.example.BarrierKU.service;

import com.example.BarrierKU.domain.Indoor.Facilities;
import com.example.BarrierKU.domain.Indoor.Room;
import com.example.BarrierKU.dto.SpaceResponse;
import com.example.BarrierKU.repository.FacilitiesRepository;
import com.example.BarrierKU.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpaceService {

    private final RoomRepository roomRepository;
    private final FacilitiesRepository facilitiesRepository;

    public SpaceResponse getResponse(Long spaceId, int type) {
        Room room = roomRepository.findById(spaceId);
        String purpose = null;

        // 편의시설인지 확인
        if(type == 0) {
            Facilities facilities = facilitiesRepository.findByName(room.getRoomName(), room.getBuilding().getNumber(), room.getFloor());
            if(facilities != null) {
                purpose = facilities.getPurpose().name();
            }
        }

        return SpaceResponse.of(room, purpose, type);
    }
}
