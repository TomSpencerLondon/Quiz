package com.example.quiz.adapter.out.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.domain.Quiz;
import com.example.quiz.domain.QuizSession;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

  @MockBean
  QuizSession quizSession;

  @MockBean
  Quiz quiz;

  @Test
  void stores_and_retrieves_Questions() {
    final QuestionDbo question = new QuestionDbo();
    question.setText("Q1");

    final QuestionDbo savedQuestion = questionJpaRepository.save(question);

    assertThat(savedQuestion.getId())
        .isNotNull()
        .isGreaterThanOrEqualTo(0);

    Optional<QuestionDbo> foundQuestion = questionJpaRepository
        .findByText("Q1");
    assertThat(foundQuestion)
        .isPresent();
  }
}
