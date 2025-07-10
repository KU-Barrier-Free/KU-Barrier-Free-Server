package com.example.BarrierKU.domain.home.service;

import com.example.BarrierKU.domain.Type.ObstacleType;
import com.example.BarrierKU.domain.home.dto.HomeLocationResponse;
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
        List<HomeLocationResponse> buildings = buildingRepository.findAll().stream()
                .map(building -> HomeLocationResponse.of(building.getId(),
                            building.getSpot().getY(),
                            building.getSpot().getX()))
                .toList();

        List<HomeLocationResponse> significants = outsideSignificantRepository.findAll().stream()
                .map(significant -> HomeLocationResponse.of(significant.getId(),
                        significant.getSpot().getY(),
                        significant.getSpot().getX()))
                .toList();

        List<HomeLocationResponse> curbs = obstacleRepository.findAll().stream()
                .filter(obstacle -> obstacle.getObstacleType().equals(CURB))
                .map(obstacle -> HomeLocationResponse.of(obstacle.getId(),
                        obstacle.getSpot().getY(),
                        obstacle.getSpot().getX()))
                .toList();

        List<HomeLocationResponse> ramps = obstacleRepository.findAll().stream()
                .filter(obstacle -> obstacle.getObstacleType().equals(RAMP))
                .map(obstacle -> HomeLocationResponse.of(obstacle.getId(),
                        obstacle.getSpot().getY(),
                        obstacle.getSpot().getX()))
                .toList();

        List<HomeLocationResponse> stairs = obstacleRepository.findAll().stream()
                .filter(obstacle -> obstacle.getObstacleType().equals(STAIR))
                .map(obstacle -> HomeLocationResponse.of(obstacle.getId(),
                        obstacle.getSpot().getY(),
                        obstacle.getSpot().getX()))
                .toList();

        return HomeResponse.of(buildings, significants, curbs, ramps, stairs);
    }
}
