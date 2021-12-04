package com.example.quiz.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import com.example.quiz.adapter.in.questionloader.QuestionFactory;
import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.Collections;
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
            "choice4", "answer"));

    final QuestionView questionView = QuestionView.of(question);

    assertThat(questionView)
        .usingRecursiveComparison()
        .isEqualTo(
            new QuestionView(
                "text",
                "answer",
                "choice1",
                "choice2",
                "choice3",
                "choice4"));
  }
}