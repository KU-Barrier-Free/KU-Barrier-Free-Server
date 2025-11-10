package com.example.BarrierKU.domain;

import com.example.BarrierKU.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloController {
    @GetMapping("/hello")
    public BaseResponse<String> home() {
        return BaseResponse.ok("🏠HELLO WORLD🏠");
    }
}
