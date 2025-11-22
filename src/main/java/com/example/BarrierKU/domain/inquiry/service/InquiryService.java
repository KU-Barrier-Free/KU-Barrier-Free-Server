package com.example.BarrierKU.domain.inquiry.service;

import com.example.BarrierKU.domain.inquiry.entity.Inquiry;
import com.example.BarrierKU.domain.inquiry.dto.InquiryRequest;
import com.example.BarrierKU.domain.inquiry.dto.InquiryResponse;
import com.example.BarrierKU.domain.inquiry.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public InquiryResponse createInquiry(InquiryRequest request) {

        String now = LocalDateTime.now()
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Inquiry inquiry = new Inquiry(request.getContent(), now);
        Inquiry saved = inquiryRepository.save(inquiry);

        return new InquiryResponse(
                saved.getId(),
                saved.getContent(),
                saved.getCreatedAt()
        );
    }
}
