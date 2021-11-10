package com.example.quiz.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import com.example.quiz.adapter.port.repository.jpa.QuestionRepositoryJpaAdapter;
import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class QuestionRepositoryJpaAdapterTest {

  @Autowired
  QuestionRepositoryJpaAdapter questionRepositoryJpaAdapter;

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
}
