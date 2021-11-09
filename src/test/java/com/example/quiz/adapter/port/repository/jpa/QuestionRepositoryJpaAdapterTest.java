package com.example.quiz.adapter.port.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.adapter.port.repository.jpa.QuestionJpaRepository;
import com.example.quiz.adapter.port.repository.jpa.QuestionRepositoryJpaAdapter;
import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.port.QuestionRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
@Tag("integration")
public class QuestionRepositoryJpaAdapterTest {

  @Autowired
  QuestionJpaRepository questionJpaRepository;

  @Autowired
  QuestionRepository questionRepository;

  @BeforeEach
  public void clear() {
    questionJpaRepository.deleteAll();
  }

  @Test
  void newlySavedQuestionHasId() {
    Question question = new Question("Q1",
        new MultipleChoice(
            new Answer("Q1A1"),
            List.of(new Answer("Q1A1"),
            new Answer("Q1A2"),
            new Answer("Q1A3"),
            new Answer("Q1A4"))));

    final Question savedQuestion = questionRepository.save(question);

    assertThat(savedQuestion).isNotNull();

    assertThat(savedQuestion.getId())
        .isNotNull();
  }
}
