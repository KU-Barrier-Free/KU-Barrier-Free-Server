package com.example.BarrierKU.domain.search.repository;

import com.example.BarrierKU.domain.search.entity.SearchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchLogRepository extends JpaRepository<SearchLog, Long> {

    @Query("""
        SELECT sl.keyword
        FROM SearchLog sl
        GROUP BY sl.keyword
        ORDER BY COUNT(sl.id) DESC
        """)
    List<String> findAllPopularKeywords();
}