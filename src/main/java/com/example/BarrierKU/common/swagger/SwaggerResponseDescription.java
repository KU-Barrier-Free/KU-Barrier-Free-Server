package com.example.BarrierKU.common.swagger;

import com.example.BarrierKU.common.response.ResponseCode;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.example.BarrierKU.common.response.ResponseCode.*;

@Getter
public enum SwaggerResponseDescription {

    TEST(new LinkedHashSet<>(Set.of(
            TEST_EXCEPTION
    ))),

    GET_BUILDING(new LinkedHashSet<>(Set.of(
            BUILDING_NOT_FOUND
    ))),

    GET_FACILITY(new LinkedHashSet<>(Set.of(
            FACILITY_NOT_FOUND
    )));

    private final Set<ResponseCode> responseCodeSet;

    SwaggerResponseDescription(Set<ResponseCode> responseCodeSet) {
        responseCodeSet.addAll(new LinkedHashSet<>(Set.of(
                SUCCESS,
                INVALID_PATH_VARIABLE_TYPE,
                BAD_REQUEST,
                API_NOT_FOUND,
                METHOD_NOT_ALLOWED,
                INTERNAL_SERVER_ERROR
        )));

        this.responseCodeSet = responseCodeSet;
    }
}
