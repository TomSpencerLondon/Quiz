package com.example.quiz.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.application.QuestionService;
import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.QuestionRepository;
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

    final AddQuestionForm addQuestionForm = new AddQuestionForm(
        "question", new ChoiceForm("a1", true),
        new ChoiceForm("a2", false),
        new ChoiceForm("a3", false),
        new ChoiceForm("a4", false));

    addQuestionForm.setChoice1(new ChoiceForm("1", false));
    addQuestionForm.setChoice1(new ChoiceForm("2", false));


    final String redirectPage = quizController.addQuestion(addQuestionForm);

    assertThat(redirectPage)
        .isEqualTo("redirect:/add-question");
    assertThat(questionRepository.findAll())
        .hasSize(1);
  }
}
