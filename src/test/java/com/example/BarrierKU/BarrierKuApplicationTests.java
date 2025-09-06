package com.example.BarrierKU;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BarrierKuApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    void ensurePostgisExtension() {
        // 테스트 DB에 PostGIS 확장이 활성화되어 있지 않다면 생성
        jdbcTemplate.execute("CREATE EXTENSION IF NOT EXISTS postgis");
    }

    @Test
    void contextLoads() {
    }

}
