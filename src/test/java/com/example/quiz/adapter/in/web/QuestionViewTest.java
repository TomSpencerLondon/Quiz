package com.example.quiz.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.adapter.in.questionloader.QuestionFactory;
import com.example.quiz.domain.Question;
import java.util.List;
import org.junit.jupiter.api.Test;

class QuestionViewTest {
  private final QuestionFactory questionFactory = new QuestionFactory();

  @Test
  void questionTransformsToQuestionView() {
    final Question question = questionFactory
        .createQuestion(List.of("text", "choice1",
            "choice2",
            "choice3",
            "choice4", "choice1"));

    final QuestionView questionView = QuestionView.of(question);

    assertThat(questionView)
        .usingRecursiveComparison()
        .isEqualTo(
            new QuestionView(
                "text",
                "choice1",
                "choice1",
                "choice2",
                "choice3",
                "choice4"));
  }
}