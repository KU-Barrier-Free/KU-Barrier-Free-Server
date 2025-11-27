package com.example.BarrierKU.domain.search.service;

import com.example.BarrierKU.domain.search.entity.SearchLog;
import com.example.BarrierKU.domain.search.repository.SearchLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchKeywordService {

    private final SearchLogRepository searchLogRepository;

    @Transactional
    public void recordSearch(String keyword) {
        String normalized = keyword.trim().toLowerCase();
        searchLogRepository.save(new SearchLog(normalized));
    }

    @Transactional(readOnly = true)
    public List<String> getTopKeywords(int limit) {
        return searchLogRepository.findAllPopularKeywords()
                .stream()
                .limit(limit)
                .toList();
    }
}
