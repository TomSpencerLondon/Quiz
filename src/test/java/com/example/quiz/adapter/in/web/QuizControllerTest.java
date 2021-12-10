package com.example.quiz.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.application.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.ConcurrentModel;

@ExtendWith(MockitoExtension.class)
public class QuizControllerTest {

  @Mock
  private QuestionService questionService;

  @InjectMocks
  QuizController quizController;

  @Test
  void viewQuestionsCreatesModelWithQuestions() {
    final ConcurrentModel model = new ConcurrentModel();
    final String viewName = quizController.viewQuestions(model);
    assertThat(viewName)
        .isEqualTo("view-questions");

    assertThat(model.containsAttribute("questions"))
        .isTrue();
  }
}
