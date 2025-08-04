package com.example.BarrierKU.domain.path.controller;

import com.example.BarrierKU.common.annotation.CustomExceptionDescription;
import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.domain.path.service.DataLoadingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.BarrierKU.common.swagger.SwaggerResponseDescription.DEFAULT;

@Tag(name = "Path", description = "Path API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/path")
public class PathController {

    private final DataLoadingService dataLoadingService;

    @GetMapping("/initialize-data")
    @CustomExceptionDescription(DEFAULT)
    public BaseResponse<Void> loadNodeLinkData() {
        log.debug("노드/링크 데이터 초기화");
        dataLoadingService.loadAllData();
        return BaseResponse.ok(null);
    }
}
