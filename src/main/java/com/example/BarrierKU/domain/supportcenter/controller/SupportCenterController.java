package com.example.BarrierKU.domain.supportcenter.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Support", description = "Support Center API")
@RestController
@RequestMapping("/support-center")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
}
