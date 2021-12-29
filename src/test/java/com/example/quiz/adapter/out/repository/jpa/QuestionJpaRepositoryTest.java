package com.example.quiz.adapter.out.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.domain.quiz.Quiz;
import com.example.quiz.domain.quiz.QuizSession;
import java.util.List;
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
    final MultipleChoice multipleChoice = new MultipleChoice();
    final Question question = new Question();
    multipleChoice.setCorrect("Q1A1");
    multipleChoice.setAnswers(List.of("Q1A1", "Q1A2", "Q1A3", "Q1A4"));
    question.setText("Q1");
    question.setMultipleChoice(multipleChoice);

    final Question savedQuestion = questionJpaRepository.save(question);

    assertThat(savedQuestion.getId())
        .isNotNull()
        .isGreaterThanOrEqualTo(0);

    Optional<Question> foundQuestion = questionJpaRepository
        .findByText("Q1");
    assertThat(foundQuestion)
        .isPresent();
  }
}
