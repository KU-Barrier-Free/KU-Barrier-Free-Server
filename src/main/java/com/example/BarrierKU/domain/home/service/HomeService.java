package com.example.BarrierKU.domain.home.service;

import com.example.BarrierKU.common.exception.BarrierKuException;
import com.example.BarrierKU.domain.building.repository.BuildingRepository;
import com.example.BarrierKU.domain.home.dto.*;
import com.example.BarrierKU.domain.home.repository.GateRepository;
import com.example.BarrierKU.domain.home.repository.ObstacleRepository;
import com.example.BarrierKU.domain.home.repository.OutsideSignificantRepository;
import com.example.BarrierKU.domain.image.OutsideSignificantImage;
import com.example.BarrierKU.domain.outdoor.Gate;
import com.example.BarrierKU.domain.outdoor.Obstacle;
import com.example.BarrierKU.domain.outdoor.OutsideSignificant;
import com.example.BarrierKU.domain.type.ObstacleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.BarrierKU.common.response.ResponseCode.*;
import static com.example.BarrierKU.domain.type.ObstacleType.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HomeService {

    private final BuildingRepository buildingRepository;
    private final ObstacleRepository obstacleRepository;
    private final OutsideSignificantRepository outsideSignificantRepository;
    private final GateRepository gateRepository;

    public HomeResponse getHomeInfo() {
        List<HomeBuildingItem> buildings = buildingRepository.findAll().stream()
                .map(building -> HomeBuildingItem.of(building.getId(),
                            building.getName(),
                            building.getSpot().getY(),
                            building.getSpot().getX()))
                .toList();

        List<HomeEtcItem> significants = outsideSignificantRepository.findAll().stream()
                .map(significant -> HomeEtcItem.of(significant.getId(),
                        significant.getSpot().getY(),
                        significant.getSpot().getX()))
                .toList();

        List<Obstacle> obstaclesList = obstacleRepository.findAll();

        List<HomeEtcItem> curbs = getHomeItemWithObstacleType(obstaclesList, CURB);

        List<HomeEtcItem> ramps = getHomeItemWithObstacleType(obstaclesList, RAMP);

        List<HomeEtcItem> stairs = getHomeItemWithObstacleType(obstaclesList, STAIR);

        List<HomeGateItem> gates =
                gateRepository.findAll().stream()
                        .map(gate -> HomeGateItem.of(
                                gate.getId(),
                                gate.getName(),
                                gate.getSpot().getY(),
                                gate.getSpot().getX()
                        ))
                        .toList();

        return HomeResponse.of(buildings, significants, curbs, ramps, stairs, gates);
    }

    public OutsideSignificantResponse getOutsideSignificantInfo(Long outsideSignificantId) {
        OutsideSignificant outsideSignificant = outsideSignificantRepository.findOutsideSignificantWithImages(outsideSignificantId)
                .orElseThrow(() -> new BarrierKuException(OUTSIDE_SIGNIFICANT_NOT_FOUND));

        return OutsideSignificantResponse.of(
                outsideSignificant.getOutsideSignificantImages().stream().map(OutsideSignificantImage::getUrl).toList(),
                outsideSignificant.getDescription(),
                outsideSignificant.getSpot().getY(),
                outsideSignificant.getSpot().getX());
    }

    public GateResponse getGateInfo(Long gateId) {
        Gate gate = gateRepository.findById(gateId)
                .orElseThrow(() -> new BarrierKuException(GATE_NOT_FOUND));

        return GateResponse.of(
                gate.getImageUrl(),
                gate.getDescription(),
                gate.getSpot().getY(),
                gate.getSpot().getX()
        );
    }

    private List<HomeEtcItem> getHomeItemWithObstacleType(List<Obstacle> obstacleList, ObstacleType type) {
        return obstacleList.stream()
                .filter(obstacle -> obstacle.getObstacleType().equals(type))
                .map(obstacle -> HomeEtcItem.of(obstacle.getId(),
                        obstacle.getSpot().getY(),
                        obstacle.getSpot().getX()))
                .toList();
    }
}
