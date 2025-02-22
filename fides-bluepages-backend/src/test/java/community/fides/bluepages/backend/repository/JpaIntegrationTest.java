package community.fides.bluepages.backend.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@Slf4j
public class JpaIntegrationTest {

    static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = (MySQLContainer) new MySQLContainer("mysql:latest").withReuse(true);
        MY_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        log.info("MY_SQL_CONTAINER.getJdbcUrl(): {}", MY_SQL_CONTAINER.getJdbcUrl());
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
        registry.add("spring.flyway.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.flyway.user", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.flyway.password", MY_SQL_CONTAINER::getPassword);
        registry.add("spring.jpa.show-sql", () -> "false");
    }

}
