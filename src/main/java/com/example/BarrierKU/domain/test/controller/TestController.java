package com.example.BarrierKU.domain.test.controller;

import com.example.BarrierKU.common.annotation.CustomExceptionDescription;
import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.domain.test.dto.response.TestResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.BarrierKU.common.response.ResponseCode.*;
import static com.example.BarrierKU.common.swagger.SwaggerResponseDescription.*;

@Tag(name = "Test", description = "테스트 API")
@RestController
@RequestMapping("/test")
public class TestController {

    @Operation(
            summary = "테스트 API",
            description = "Swagger 공통 응답 및 예외 처리 테스트용 API"
    )
    @GetMapping
    @CustomExceptionDescription(TEST)
    public BaseResponse<TestResponseDTO> testApi() {
        return BaseResponse.ok(new TestResponseDTO("테스트 예시 응답"));
    }
}
