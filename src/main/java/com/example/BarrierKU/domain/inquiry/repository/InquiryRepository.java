package com.example.BarrierKU.domain.inquiry.repository;

import com.example.BarrierKU.domain.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
