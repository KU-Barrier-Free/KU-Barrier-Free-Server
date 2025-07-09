package com.example.BarrierKU.service;

import com.example.BarrierKU.common.exception.BarrierKuException;
import com.example.BarrierKU.domain.Indoor.Room;
import com.example.BarrierKU.dto.SpaceResponse;
import com.example.BarrierKU.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.BarrierKU.common.response.ResponseCode.SPACE_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpaceService {

    private final RoomRepository roomRepository;

    public SpaceResponse getSpaceInfo(Long spaceId, int type) {
        Room room = roomRepository.findById(spaceId)
                .orElseThrow(() -> new BarrierKuException(SPACE_NOT_FOUND));
        return SpaceResponse.of(room, type);
    }
}
