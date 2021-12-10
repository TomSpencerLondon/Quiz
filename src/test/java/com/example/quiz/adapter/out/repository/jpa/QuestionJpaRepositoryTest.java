package com.example.quiz.adapter.out.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Tag("integration")
@DataJpaTest(properties = {
    "spring.flyway.enabled=false",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Testcontainers(disabledWithoutDocker = true)
public class QuestionJpaRepositoryTest {

  @Container
  static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:12.3")
      .withDatabaseName("test")
      .withUsername("duke")
      .withPassword("s3cret");

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Autowired
  private QuestionJpaRepository questionJpaRepository;

  @Test
  void stores_and_retrieves_Questions() {
    final MultipleChoiceDto multipleChoiceDto = new MultipleChoiceDto();
    final QuestionDto questionDto = new QuestionDto();
    multipleChoiceDto.setCorrect("Q1A1");
    multipleChoiceDto.setAnswers(List.of("Q1A1", "Q1A2", "Q1A3", "Q1A4"));
    questionDto.setText("Q1");
    questionDto.setMultipleChoiceDto(multipleChoiceDto);

    final QuestionDto savedQuestion = questionJpaRepository.save(questionDto);

    assertThat(savedQuestion.getId())
        .isNotNull()
        .isGreaterThanOrEqualTo(0);

    Optional<QuestionDto> foundQuestion = questionJpaRepository
        .findByText("Q1");
    assertThat(foundQuestion)
        .isPresent();
  }
}
