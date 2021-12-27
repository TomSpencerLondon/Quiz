package com.example.quiz;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers(disabledWithoutDocker = true)
public interface IntegrationConfiguration {
  @Container
  static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:12.3")
      .withDatabaseName("test")
      .withUsername("duke")
      .withPassword("s3cret");

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.sql.init.platform", () -> "postgresql");
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }
}
