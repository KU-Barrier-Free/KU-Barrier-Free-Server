package com.example.BarrierKU.domain.path.service;

import com.example.BarrierKU.domain.path.model.Crossing;
import com.example.BarrierKU.domain.path.model.Node;
import com.example.BarrierKU.domain.path.model.Street;
import com.example.BarrierKU.domain.path.model.Way;
import com.example.BarrierKU.domain.path.repository.CrossingRepository;
import com.example.BarrierKU.domain.path.repository.NodeRepository;
import com.example.BarrierKU.domain.path.repository.StreetRepository;
import com.example.BarrierKU.domain.path.repository.WayRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.geotools.api.referencing.FactoryException;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.api.referencing.operation.MathTransform;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataLoadingService {

    private final NodeRepository nodeRepository;
    private final WayRepository wayRepository;
    private final CrossingRepository crossingRepository;
    private final StreetRepository streetRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private GeometryFactory geometryFactory4326;
    private MathTransform transform5179to4326;

    @Transactional
    public void loadAllData() {
        try {
            initialize();
            loadNodes("geoJson/nodeJson.geojson");
            loadWays("geoJson/wayJson.geojson");
            loadCrossings("geoJson/crossingJson.geojson");
            loadStreets("geoJson/streetJson.geojson");
            log.info("모든 데이터 로딩 완료");
        } catch (Exception e) {
            throw new IllegalStateException("데이터 로딩 중 오류 발생", e);
        }
    }

    private void initialize() throws FactoryException {
        System.setProperty("org.geotools.referencing.forceXY", "true");
        geometryFactory4326 = new GeometryFactory(new PrecisionModel(), 4326);
        CoordinateReferenceSystem sourceCrs = CRS.decode("EPSG:5179");
        CoordinateReferenceSystem targetCrs = DefaultGeographicCRS.WGS84;
        transform5179to4326 = CRS.findMathTransform(sourceCrs, targetCrs, true);
        log.info("좌표 변환 시스템 초기화 완료");
    }

    private void loadNodes(String path) throws Exception {
        List<Map<String, Object>> features = readFeatures(path);
        List<Node> nodes = new ArrayList<>();

        for (Map<String, Object> feature : features) {
            Map<String, Object> properties = (Map<String, Object>) feature.get("properties");
            Map<String, Object> geometry = (Map<String, Object>) feature.get("geometry");
            List<Double> coords = (List<Double>) geometry.get("coordinates");

            double x = coords.get(0);
            double y = coords.get(1);
            Point transformed = transformPoint(x, y);

            Node node = new Node(
                    null,
                    (String) properties.get("uid"),
                    (Integer) properties.get("sig_cd"),
                    (Integer) properties.get("type"),
                    transformed
            );

            nodes.add(node);
        }

        nodeRepository.saveAll(nodes);
        log.info("노드 {}개 저장 완료", nodes.size());
    }

    private void loadWays(String path) throws Exception {
        List<Map<String, Object>> features = readFeatures(path);
        List<Way> ways = new ArrayList<>();

        for (Map<String, Object> feature : features) {
            Map<String, Object> props = (Map<String, Object>) feature.get("properties");
            Way way = objectMapper.convertValue(props, Way.class);
            ways.add(way);
        }

        wayRepository.saveAll(ways);
        log.info("웨이 {}개 저장 완료", ways.size());
    }

    private void loadCrossings(String path) throws Exception {
        List<Map<String, Object>> features = readFeatures(path);
        List<Crossing> crossings = new ArrayList<>();

        for (Map<String, Object> feature : features) {
            Map<String, Object> props = (Map<String, Object>) feature.get("properties");
            Crossing crossing = objectMapper.convertValue(props, Crossing.class);
            crossings.add(crossing);
        }

        crossingRepository.saveAll(crossings);
        log.info("횡단보도 {}개 저장 완료", crossings.size());
    }

    private void loadStreets(String path) throws Exception {
        List<Map<String, Object>> features = readFeatures(path);
        List<Street> streets = new ArrayList<>();

        for (Map<String, Object> feature : features) {
            Map<String, Object> props = (Map<String, Object>) feature.get("properties");
            Street street = objectMapper.convertValue(props, Street.class);
            streets.add(street);
        }

        streetRepository.saveAll(streets);
        log.info("도로 {}개 저장 완료", streets.size());
    }

    private List<Map<String, Object>> readFeatures(String path) throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalArgumentException("파일을 찾을 수 없습니다: " + path);
            }

            JsonNode root = objectMapper.readTree(is);
            JsonNode features = root.get("features");
            return objectMapper.convertValue(features, new TypeReference<List<Map<String, Object>>>() {});
        }
    }

    private Point transformPoint(double x5179, double y5179) throws Exception {
        Coordinate source = new Coordinate(x5179, y5179);
        Point point5179 = new GeometryFactory(new PrecisionModel(), 5179).createPoint(source);
        Geometry transformed = JTS.transform(point5179, transform5179to4326);
        return geometryFactory4326.createPoint(transformed.getCoordinate());
    }
}
