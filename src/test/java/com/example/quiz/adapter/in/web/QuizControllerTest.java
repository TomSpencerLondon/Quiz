package com.example.quiz.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.application.QuestionService;
import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.ResponseStatus;
import com.example.quiz.domain.port.InMemoryQuestionRepository;
import com.example.quiz.domain.port.QuestionRepository;
import com.example.quiz.domain.quiz.MultipleChoiceQuestionFactory;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

public class QuizControllerTest {

  @Test
  void viewQuestionsCreatesModelWithQuestions() {
    QuestionRepository questionRepository = new InMemoryQuestionRepository();
    QuestionService questionService = new QuestionService(questionRepository);
    WebQuiz webQuiz = new WebQuiz(questionRepository);
    WebQuizSession webQuizSession = new WebQuizSession(webQuiz);
    QuizController quizController = new QuizController(questionService, webQuizSession);

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
    WebQuiz webQuiz = new WebQuiz(questionRepository);
    WebQuizSession webQuizSession = new WebQuizSession(webQuiz);
    QuizController quizController = new QuizController(questionService, webQuizSession);

    final AddQuestionForm addQuestionForm = new AddQuestionForm(
        "question", "a1", "a1", "a2", "a3", "a4");

    final String redirectPage = quizController.addQuestion(addQuestionForm);

    assertThat(redirectPage)
        .isEqualTo("redirect:/add-question");
    assertThat(questionRepository.findAll())
        .hasSize(1);
  }

  @Test
  void asksOneQuestion() {
    QuestionRepository questionRepository = new InMemoryQuestionRepository();
    questionRepository.save(MultipleChoiceQuestionFactory.createMultipleChoiceQuestion());
    QuestionService questionService = new QuestionService(questionRepository);
    WebQuiz webQuiz = new WebQuiz(questionRepository);
    WebQuizSession webQuizSession = new WebQuizSession(webQuiz);
    QuizController quizController = new QuizController(questionService, webQuizSession);

    final Model model = new ConcurrentModel();

    quizController.askQuestion(model);

    final AskQuestionForm question = (AskQuestionForm) model.getAttribute("askQuestionForm");

    assertThat(question.getQuestion())
        .isEqualTo("Question 1");
  }

  @Test
  void storesFormResponseAnswerInQuizSessionMarkedAsCorrectAnswer() {
    QuestionRepository questionRepository = new InMemoryQuestionRepository();
    Question question = new Question(
        "Question 1",
        new MultipleChoice(new Answer("Correct Answer"),
            List.of(new Answer("Correct Answer"), new Answer("Wrong Answer"))));
    questionRepository.save(question);
    QuestionService questionService = new QuestionService(questionRepository);

    WebQuiz webQuiz = new WebQuiz(questionRepository);
    WebQuizSession webQuizSession = new WebQuizSession(webQuiz);
    QuizController quizController = new QuizController(questionService, webQuizSession);
    final Model model = new ConcurrentModel();
    quizController.askQuestion(model);

    AskQuestionForm askQuestionForm = new AskQuestionForm();
    askQuestionForm.setSelectedChoice("Correct Answer");
    quizController.answerQuestion(askQuestionForm);

    assertThat(webQuizSession.lastResponseStatus())
        .isEqualTo(ResponseStatus.CORRECT);
  }
}
