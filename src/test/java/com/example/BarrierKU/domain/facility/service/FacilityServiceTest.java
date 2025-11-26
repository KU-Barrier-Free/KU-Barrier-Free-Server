package com.example.BarrierKU.domain.facility.service;

import com.example.BarrierKU.common.exception.BarrierKuException;
import com.example.BarrierKU.domain.facility.dto.FacilitiesResponse;
import com.example.BarrierKU.domain.facility.repository.FacilityRepository;
import com.example.BarrierKU.domain.indoor.Building;
import com.example.BarrierKU.domain.indoor.Facilities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.BarrierKU.domain.type.Purpose.CAFE;
import static com.example.BarrierKU.utils.TestGeometryUtils.point;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class FacilityServiceTest {
    @Mock
    private FacilityRepository facilityRepository;

    @InjectMocks
    private FacilityService facilityService;

    @Test
    @DisplayName("getFacility: 존재하는 ID면 상세 응답을 매핑하여 반환한다")
    void getFacility_success() {
        // given
        Long facilityId = 1L;
        Long buildingId = 10L;

        Facilities facility = Mockito.mock(Facilities.class);
        Building building = Mockito.mock(Building.class);

        given(facility.getId()).willReturn(facilityId);
        given(facility.getName()).willReturn("레스티오");
        given(facility.getPurpose()).willReturn(CAFE);
        given(facility.getBuilding()).willReturn(building);
        given(building.getId()).willReturn(buildingId);
        given(building.getSpot()).willReturn(point(127.0260, 37.5900));

        given(facilityRepository.findWithBuildingById(facilityId)).willReturn(Optional.of(facility));

        // when
        FacilitiesResponse response = facilityService.getFacility(facilityId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.facilityId()).isEqualTo(facilityId);
        assertThat(response.facilityName()).isEqualTo("레스티오");
        assertThat(response.purpose()).isEqualTo("CAFE");
        assertThat(response.buildingInfo().id()).isEqualTo(buildingId);
        assertThat(response.buildingInfo().latitude()).isCloseTo(37.5900, within(1e-4));
        assertThat(response.buildingInfo().longitude()).isCloseTo(127.0260, within(1e-4));

        verify(facilityRepository).findWithBuildingById(facilityId);
    }

    @Test
    @DisplayName("getFacility: 존재하지 않는 ID면 BarrierKuException을 던진다")
    void getFacility_notFound() {
        // given
        Long facilityId = 999L;
        given(facilityRepository.findWithBuildingById(facilityId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> facilityService.getFacility(facilityId))
                .isInstanceOf(BarrierKuException.class);

        verify(facilityRepository).findWithBuildingById(facilityId);
    }

    @Test
    @DisplayName("getFacilitiesByBuildingId: 레포지토리 결과를 그대로 반환한다")
    void getFacilitiesByBuildingId_success() {
        // given
        Long buildingId = 10L;
        Facilities f1 = Mockito.mock(Facilities.class);
        Facilities f2 = Mockito.mock(Facilities.class);
        given(facilityRepository.findAllByBuildingId(buildingId)).willReturn(List.of(f1, f2));

        // when
        List<Facilities> list = facilityService.getFacilitiesByBuildingId(buildingId);

        // then
        assertThat(list).hasSize(2).containsExactly(f1, f2);
        verify(facilityRepository).findAllByBuildingId(buildingId);
    }

    @Test
    @DisplayName("getFacilitiesByName: 대소문자 무시 contains 검색 결과를 그대로 반환한다")
    void getFacilitiesByName_success() {
        // given
        String keyword = "레";
        Facilities f1 = Mockito.mock(Facilities.class);
        given(facilityRepository.findByNameOrSynonym(keyword)).willReturn(List.of(f1));

        // when
        List<Facilities> list = facilityService.getFacilitiesByName(keyword);

        // then
        assertThat(list).hasSize(1).containsExactly(f1);
        verify(facilityRepository).findByNameOrSynonym(keyword);
    }

}