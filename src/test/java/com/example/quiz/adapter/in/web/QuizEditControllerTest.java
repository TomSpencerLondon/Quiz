package com.example.quiz.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.application.QuestionService;
import com.example.quiz.domain.port.InMemoryQuestionRepository;
import com.example.quiz.domain.port.QuestionRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

public class QuizEditControllerTest {

  @Test
  void viewQuestionsCreatesModelWithQuestions() {
    QuestionRepository questionRepository = new InMemoryQuestionRepository();
    QuestionService questionService = new QuestionService(questionRepository);
    QuizEditController quizController = new QuizEditController(questionService);

    final Model model = new ConcurrentModel();
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
    QuizEditController quizController = new QuizEditController(questionService);

    final List<String> correctAnswers = List.of("1", "2");
    final AddQuestionForm addQuestionForm = new AddQuestionForm(
        "question", "a1", "a2", "a3", "a4");

    addQuestionForm.setChoice1("1");
    addQuestionForm.setChoice1("2");


    final String redirectPage = quizController.addQuestion(addQuestionForm, correctAnswers);

    assertThat(redirectPage)
        .isEqualTo("redirect:/add-question");
    assertThat(questionRepository.findAll())
        .hasSize(1);
  }
}
