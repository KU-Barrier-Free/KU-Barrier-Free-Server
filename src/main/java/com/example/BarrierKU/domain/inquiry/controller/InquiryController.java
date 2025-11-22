package com.example.BarrierKU.domain.inquiry.controller;

import com.example.BarrierKU.common.response.BaseResponse;
import com.example.BarrierKU.domain.inquiry.dto.InquiryRequest;
import com.example.BarrierKU.domain.inquiry.dto.InquiryResponse;
import com.example.BarrierKU.domain.inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping
    public BaseResponse<InquiryResponse> createInquiry(@RequestBody InquiryRequest request) {
        log.info("Inquiry content = {}", request.getContent());
        InquiryResponse response = inquiryService.createInquiry(request);
        return BaseResponse.ok(response);
    }
}
