package com.example.BarrierKU;

import com.example.BarrierKU.config.PostgresTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ActiveProfiles("test")
@Import(PostgresTestConfig.class)
class BarrierKuApplicationTests {
    @Test
    void contextLoads() {
    }

}
