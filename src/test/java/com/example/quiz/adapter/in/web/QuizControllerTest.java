package com.example.quiz.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;

public class QuizControllerTest {



  @Test
  void viewQuestionsCreatesModelWithQuestions() {
    final QuizController quizController = new QuizController(null);
    final ConcurrentModel model = new ConcurrentModel();
    final String viewName = quizController.viewQuestions(model);
    assertThat(viewName)
        .isEqualTo("view-questions");

    assertThat(model.containsAttribute("questions"))
        .isTrue();
  }
}
