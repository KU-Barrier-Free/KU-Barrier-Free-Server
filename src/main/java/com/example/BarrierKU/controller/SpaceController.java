package com.example.BarrierKU.controller;

import com.example.BarrierKU.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("buildings/")
@RequiredArgsConstructor
public class SpaceController {

    private final SpaceService spaceService;
}
