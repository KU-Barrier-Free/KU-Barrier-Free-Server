package com.example.BarrierKU.domain.facility.controller;

import com.example.BarrierKU.common.exception.BarrierKuException;
import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.domain.facility.dto.FacilitiesResponse;
import com.example.BarrierKU.domain.facility.service.FacilityService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.BarrierKU.common.response.ResponseCode.FACILITY_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FacilityControllerTest {

    @Mock
    private FacilityService facilityService;

    @InjectMocks
    private FacilityController facilityController;

    @Test
    @DisplayName("편의시설 상세 조회 성공 시: 서비스에서 받은 응답을 BaseResponse로 감싸 반환한다")
    void getFacility_success() {
        // given
        Long facilityId = 1L;
        FacilitiesResponse mockResponse = Mockito.mock(FacilitiesResponse.class);
        given(facilityService.getFacility(facilityId)).willReturn(mockResponse);

        // when
        BaseResponse<FacilitiesResponse> result = facilityController.getFacility(facilityId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getResult()).isSameAs(mockResponse); // 동일 인스턴스 반환 검증
        verify(facilityService).getFacility(facilityId);
    }

    @Test
    @DisplayName("편의시설 상세 조회 실패 시: 존재하지 않는 facilityId로 요청할 경우 예외가 발생한다")
    void getFacility_notFound() {
        // given
        Long facilityId = 999L;
        given(facilityService.getFacility(facilityId)).willThrow(new BarrierKuException(FACILITY_NOT_FOUND));

        // when & then
        Assertions.assertThatThrownBy(() -> facilityController.getFacility(facilityId))
                .isInstanceOf(BarrierKuException.class)
                .hasMessageContaining("해당 편의시설을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("편의시설 상세 조회 실패 시: 잘못된 파라미터(null)로 요청할 경우 예외가 발생한다")
    void getFacility_invalidParameter() {
        // given
        Long facilityId = null;
        given(facilityService.getFacility(null)).willThrow(new BarrierKuException(FACILITY_NOT_FOUND));

        // when & then
        Assertions.assertThatThrownBy(() -> facilityController.getFacility(facilityId))
                .isInstanceOf(BarrierKuException.class)
                .hasMessageContaining("해당 편의시설을 찾을 수 없습니다.");
    }
}
