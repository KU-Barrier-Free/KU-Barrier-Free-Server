package com.example.BarrierKU.domain.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryResponse {

    private Long inquiryId;
    private String content;
    private String createdAt;
}
