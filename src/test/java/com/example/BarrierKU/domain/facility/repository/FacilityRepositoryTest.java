package com.example.BarrierKU.domain.facility.repository;

import com.example.BarrierKU.domain.building.repository.BuildingRepository;
import com.example.BarrierKU.domain.indoor.Building;
import com.example.BarrierKU.domain.indoor.Facilities;
import com.example.BarrierKU.domain.type.Purpose;
import com.example.BarrierKU.utils.TestGeometryUtils;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnitUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FacilityRepositoryTest {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    EntityManagerFactory emf;

    private Facilities newFacilities(String name, Purpose purpose, String floor, Building building) {
        Facilities f = new Facilities();
        ReflectionTestUtils.setField(f, "name", name);
        ReflectionTestUtils.setField(f, "purpose", purpose);
        ReflectionTestUtils.setField(f, "floor", floor);
        ReflectionTestUtils.setField(f, "building", building);
        return f;
    }

    private Building newBuilding(int number, String name, org.locationtech.jts.geom.Point spot, boolean lecture) {
        Building b = new Building();
        ReflectionTestUtils.setField(b, "buildingNumber", number);
        ReflectionTestUtils.setField(b, "name", name);
        ReflectionTestUtils.setField(b, "spot", spot);
        ReflectionTestUtils.setField(b, "lecture", lecture);
        ReflectionTestUtils.setField(b, "image", "");
        return b;
    }

    @Test
    @DisplayName("findWithBuildingById: 건물까지 fetch join 되어 조회된다")
    void findWithBuildingById_fetchJoin() {
        // given
        Building building = buildingRepository.save(newBuilding(21, "공학관", TestGeometryUtils.point(127.078791, 37.541668), true));
        Facilities facility = facilityRepository.save(newFacilities("레스티오", Purpose.CAFE, "1", building));

        // when
        Facilities loaded = facilityRepository.findWithBuildingById(facility.getId()).orElseThrow();

        // then
        assertThat(loaded.getName()).isEqualTo("레스티오");
        assertThat(loaded.getBuilding().getName()).isEqualTo("공학관");

        // fetch join 로딩 상태 간접 확인
        PersistenceUnitUtil util = emf.getPersistenceUnitUtil();
        assertThat(util.isLoaded(loaded.getBuilding())).isTrue();
    }

    @Test
    @DisplayName("findByNameContainingIgnoreCase: 대소문자 무시 부분 검색")
    void findByNameContainingIgnoreCase() {
        // given
        Building building = buildingRepository.save(newBuilding(2, "경영관", TestGeometryUtils.point(127.076069, 37.544311), true));

        Facilities f1 = newFacilities("K-Hub", Purpose.KHUB, "1", building);
        Facilities f2 = newFacilities("CU", Purpose.CONVINIENCE, "1", building);
        facilityRepository.saveAll(List.of(f1, f2));

        // when
        List<Facilities> result = facilityRepository.findByNameContainingIgnoreCase("u");

        // then
        assertThat(result).extracting(Facilities::getName)
                .containsExactlyInAnyOrder("K-Hub", "CU");
    }

    @Test
    @DisplayName("findAllByBuildingId: 특정 건물 ID에 속한 편의시설만 반환")
    void findAllByBuildingId() {
        // given
        Building building1 = newBuilding(9, "상허기념도서관", TestGeometryUtils.point(127.07373, 37.54203), false);
        Building building2 = newBuilding(20, "학생회관", TestGeometryUtils.point(127.078206, 37.541921), false);
        buildingRepository.saveAll(List.of(building1, building2));

        Facilities f1 = newFacilities("1847샐러드카페", Purpose.CAFE, "B1", building1);
        Facilities f2 = newFacilities("CU", Purpose.CONVINIENCE, "3", building1);
        Facilities f3 = newFacilities("휴게실", Purpose.FOYER, "3", building1);
        Facilities f4 = newFacilities("1847샐러드카페", Purpose.CAFE, "1", building2);
        Facilities f5 = newFacilities("CU", Purpose.CONVINIENCE, "1", building2);
        Facilities f6 = newFacilities("장애학생휴게실", Purpose.FOYER, "1", building2);
        facilityRepository.saveAll(List.of(f1, f2, f3, f4, f5, f6));

        // when
        List<Facilities> result = facilityRepository.findAllByBuildingId(building1.getId());

        // then
        assertThat(result).extracting(Facilities::getName)
                .containsExactlyInAnyOrder("1847샐러드카페", "CU", "휴게실");
    }
}
