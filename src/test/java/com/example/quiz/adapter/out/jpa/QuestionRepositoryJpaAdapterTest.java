package com.example.quiz.adapter.out.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.IntegrationConfiguration;
import com.example.quiz.adapter.out.repository.jpa.QuestionJpaRepository;
import com.example.quiz.adapter.out.repository.jpa.QuestionRepositoryJpaAdapter;
import com.example.quiz.adapter.out.repository.jpa.QuestionTransformer;
import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Transactional
public class QuestionRepositoryJpaAdapterTest implements IntegrationConfiguration {

  @Autowired
  QuestionJpaRepository questionJpaRepository;

  @Autowired
  QuestionTransformer questionTransformer;

  @BeforeEach
  public void clear() {
    questionJpaRepository.deleteAll();
  }

  @Test
  void newlySavedQuestionHasId() throws Exception {
    final QuestionRepositoryJpaAdapter questionRepositoryJpaAdapter =
        new QuestionRepositoryJpaAdapter(questionJpaRepository, questionTransformer);

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
    final QuestionRepositoryJpaAdapter questionRepositoryJpaAdapter =
        new QuestionRepositoryJpaAdapter(questionJpaRepository, questionTransformer);

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
