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

    public SpaceResponse getResponse(Long spaceId, int type) {
        Room room = roomRepository.findById(spaceId);
        return SpaceResponse.of(room, type);
    }
}
