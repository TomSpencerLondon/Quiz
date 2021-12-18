package com.example.quiz.adapter.in.web;

import static com.example.quiz.domain.quiz.MultipleChoiceQuestionFactory.createMultipleChoiceQuestion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.quiz.application.QuestionService;
import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.ResponseStatus;
import com.example.quiz.domain.port.InMemoryQuestionRepository;
import com.example.quiz.domain.port.QuestionRepository;
import com.example.quiz.domain.quiz.QuizSession;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

@WebMvcTest
class QuizControllerTest {

  @Autowired MockMvc mockMvc;

  @MockBean QuestionService questionService;

  @Test
  void viewQuestionsCreatesModelWithQuestions() {
    QuestionRepository questionRepository = new InMemoryQuestionRepository();
    QuestionService questionService = new QuestionService(questionRepository);
    QuizController quizController = new QuizController(questionService);

    final Model model = new ConcurrentModel();
    final String viewName = quizController.viewQuestions(model);
    assertThat(viewName).isEqualTo("view-questions");

    assertThat(model.containsAttribute("questions")).isTrue();
  }

  @Test
  void addQuestionResultsInQuestionAddedAndRedirects() {
    QuestionRepository questionRepository = new InMemoryQuestionRepository();
    QuestionService questionService = new QuestionService(questionRepository);
    QuizController quizController = new QuizController(questionService);

    final AddQuestionForm addQuestionForm =
        new AddQuestionForm("question", "a1", "a1", "a2", "a3", "a4");

    final String redirectPage = quizController.addQuestion(addQuestionForm);

    assertThat(redirectPage).isEqualTo("redirect:/add-question");
    assertThat(questionRepository.findAll()).hasSize(1);
  }

  @Test
  void asksOneQuestion() throws Exception {
    given(this.questionService.findAll()).willReturn(List.of(createMultipleChoiceQuestion()));

    mockMvc
        .perform(get("/quiz"))
        .andExpectAll(
            status().isOk(),
            model().attribute("askQuestionForm", hasProperty("question", equalTo("Question 1"))));
  }

  @Test
  void storesFormResponseAnswerInQuizSessionMarkedAsCorrectAnswer() {
    QuestionRepository questionRepository = new InMemoryQuestionRepository();
    Question question =
        new Question(
            "Question 1",
            new MultipleChoice(
                new Answer("Correct Answer"),
                List.of(new Answer("Correct Answer"), new Answer("Wrong Answer"))));
    questionRepository.save(question);
    QuestionService questionService = new QuestionService(questionRepository);

    QuizController quizController = new QuizController(questionService);

    AskQuestionForm askQuestionForm = new AskQuestionForm();
    askQuestionForm.setSelectedChoice("Correct Answer");
    quizController.answerQuestion(askQuestionForm);
    final QuizSession quizSession = quizController.getQuizSession();

    assertThat(quizSession.lastResponseStatus()).isEqualTo(ResponseStatus.CORRECT);
  }
}
