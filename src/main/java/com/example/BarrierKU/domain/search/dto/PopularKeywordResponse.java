package com.example.BarrierKU.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PopularKeywordResponse {
    private List<String> popularKeywords;
}

