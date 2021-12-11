package com.example.quiz.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.application.QuestionService;
import com.example.quiz.domain.port.InMemoryQuestionRepository;
import com.example.quiz.domain.port.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.ConcurrentModel;

public class QuizControllerTest {


  @Test
  void viewQuestionsCreatesModelWithQuestions() {
    QuestionRepository questionRepository = new InMemoryQuestionRepository();
    QuestionService questionService = new QuestionService(questionRepository);
    QuizController quizController = new QuizController(questionService);

    final ConcurrentModel model = new ConcurrentModel();
    final String viewName = quizController.viewQuestions(model);
    assertThat(viewName)
        .isEqualTo("view-questions");

    assertThat(model.containsAttribute("questions"))
        .isTrue();
  }

  @Test
  void addQuestionResultsInQuestionAddedAndRedirects() {
    QuestionRepository questionRepository = new InMemoryQuestionRepository();
    QuestionService questionService = new QuestionService(questionRepository);
    QuizController quizController = new QuizController(questionService);

    final AddQuestionForm addQuestionForm = new AddQuestionForm(
        "question", "a1", "a1", "a2", "a3", "a4");

    final String redirectPage = quizController.addQuestion(addQuestionForm);

    assertThat(redirectPage)
        .isEqualTo("redirect:/add-question");
    assertThat(questionRepository.findAll())
        .hasSize(1);
  }
}
