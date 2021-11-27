package com.example.quiz.adapter.out.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import org.testcontainers.containers.PostgreSQLContainer;
import com.example.quiz.adapter.out.repository.jpa.QuestionJpaRepository;
import com.example.quiz.adapter.out.repository.jpa.QuestionRepositoryJpaAdapter;
import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Tag("integration")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@Testcontainers(disabledWithoutDocker = true)
public class QuestionRepositoryJpaAdapterTest {

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
  QuestionRepositoryJpaAdapter questionRepositoryJpaAdapter;

  @Autowired
  QuestionJpaRepository questionJpaRepository;

  @Test
  void newlySavedQuestionHasId() throws Exception {
    Question question = new Question("Q1",
        new MultipleChoice(
            new Answer("Q1A1"),
            List.of(new Answer("Q1A1"),
            new Answer("Q1A2"),
            new Answer("Q1A3"),
            new Answer("Q1A4"))));

    final Question savedQuestion = questionRepositoryJpaAdapter.save(question);

    assertThat(savedQuestion)
        .isNotNull();

    assertThat(savedQuestion.getId())
        .isNotNull();
  }

  @Test
  void finds_all_in_database() {
    Question question1 = new Question("Q1",
        new MultipleChoice(
            new Answer("Q1A1"),
            List.of(new Answer("Q1A1"),
                new Answer("Q1A2"),
                new Answer("Q1A3"),
                new Answer("Q1A4"))));

    Question question2 = new Question("Q2",
        new MultipleChoice(
            new Answer("Q2A1"),
            List.of(new Answer("Q2A1"),
                new Answer("Q2A2"),
                new Answer("Q2A3"),
                new Answer("Q2A4"))));
    questionRepositoryJpaAdapter.save(question1);
    questionRepositoryJpaAdapter.save(question2);

    final List<Question> foundQuestions = questionRepositoryJpaAdapter.findAll();

    assertThat(foundQuestions)
        .hasSize(2);
  }
}
