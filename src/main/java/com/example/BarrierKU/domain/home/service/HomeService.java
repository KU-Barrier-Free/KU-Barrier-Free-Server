package com.example.BarrierKU.domain.home.service;

import com.example.BarrierKU.domain.Type.ObstacleType;
import com.example.BarrierKU.domain.home.dto.HomeItem;
import com.example.BarrierKU.domain.home.dto.HomeResponse;
import com.example.BarrierKU.domain.home.repository.ObstacleRepository;
import com.example.BarrierKU.domain.home.repository.OutsideSignificantRepository;
import com.example.BarrierKU.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.BarrierKU.domain.Type.ObstacleType.*;

@RequiredArgsConstructor
@Service
public class HomeService {

    private final BuildingRepository buildingRepository;
    private final ObstacleRepository obstacleRepository;
    private final OutsideSignificantRepository outsideSignificantRepository;

    public HomeResponse getHomeInfo() {
        List<HomeItem> buildings = buildingRepository.findAll().stream()
                .map(building -> HomeItem.of(building.getId(),
                            building.getSpot().getY(),
                            building.getSpot().getX()))
                .toList();

        List<HomeItem> significants = outsideSignificantRepository.findAll().stream()
                .map(significant -> HomeItem.of(significant.getId(),
                        significant.getSpot().getY(),
                        significant.getSpot().getX()))
                .toList();

        List<HomeItem> curbs = getHomeItemWithObstacleType(CURB);

        List<HomeItem> ramps = getHomeItemWithObstacleType(RAMP);

        List<HomeItem> stairs = getHomeItemWithObstacleType(STAIR);

        return HomeResponse.of(buildings, significants, curbs, ramps, stairs);
    }


    private List<HomeItem> getHomeItemWithObstacleType(ObstacleType type) {
        return obstacleRepository.findAll().stream()
                .filter(obstacle -> obstacle.getObstacleType().equals(type))
                .map(obstacle -> HomeItem.of(obstacle.getId(),
                        obstacle.getSpot().getY(),
                        obstacle.getSpot().getX()))
                .toList();
    }
}
