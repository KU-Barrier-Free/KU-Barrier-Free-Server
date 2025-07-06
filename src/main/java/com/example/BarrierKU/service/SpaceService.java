package com.example.BarrierKU.service;

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
}
