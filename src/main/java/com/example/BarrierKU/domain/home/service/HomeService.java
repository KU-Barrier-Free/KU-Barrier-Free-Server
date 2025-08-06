package com.example.BarrierKU.domain.home.service;

import com.example.BarrierKU.common.exception.BarrierKuException;
import com.example.BarrierKU.domain.building.repository.BuildingRepository;
import com.example.BarrierKU.domain.home.dto.*;
import com.example.BarrierKU.domain.home.repository.ObstacleRepository;
import com.example.BarrierKU.domain.home.repository.OutsideSignificantRepository;
import com.example.BarrierKU.domain.image.OutsideSignificantImage;
import com.example.BarrierKU.domain.indoor.Building;
import com.example.BarrierKU.domain.outdoor.Obstacle;
import com.example.BarrierKU.domain.outdoor.OutsideSignificant;
import com.example.BarrierKU.domain.type.ObstacleType;
import com.example.BarrierKU.domain.type.Purpose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.BarrierKU.common.response.ResponseCode.*;
import static com.example.BarrierKU.domain.type.ObstacleType.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HomeService {

    private final BuildingRepository buildingRepository;
    private final ObstacleRepository obstacleRepository;
    private final OutsideSignificantRepository outsideSignificantRepository;

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

        return HomeResponse.of(buildings, significants, curbs, ramps, stairs);
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

    public BuildingInfoResponse getBuildingInfo(Long buildingId){
        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new BarrierKuException(BUILDING_NOT_FOUND));
        Set<String> buildingFacilities = building.getFacilityPurposes().stream()
                .map(Purpose::getValue).collect(Collectors.toSet());
        return new BuildingInfoResponse(buildingId, building.getNumber(), building.getName(), building.getSpot().getY(),
                building.getSpot().getX(), buildingFacilities, building.getDoors());
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
