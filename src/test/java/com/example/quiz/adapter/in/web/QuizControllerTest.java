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
import com.example.quiz.domain.quiz.Quiz;
import com.example.quiz.domain.quiz.QuizSession;
import com.example.quiz.domain.quiz.TestQuizFactory;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

public class QuizControllerTest {

  @Test
  void afterQuizStartedAskForQuestionReturnsFirstQuestion() {
    QuestionRepository questionRepository = new InMemoryQuestionRepository();
    questionRepository.save(MultipleChoiceQuestionFactory.createMultipleChoiceQuestion());
    Quiz quiz = new Quiz(questionRepository);
    QuizController quizController = new QuizController(quiz);
    quizController.start();

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

    Quiz quiz = new Quiz(questionRepository);
    QuizSession quizSession = new QuizSession(quiz);
    QuizController quizController = new QuizController(quizSession);
    final Model model = new ConcurrentModel();
    quizController.askQuestion(model);

    AskQuestionForm askQuestionForm = new AskQuestionForm();
    askQuestionForm.setSelectedChoice("Correct Answer");
    quizController.questionResponse(askQuestionForm);

    assertThat(quizSession.lastResponseStatus())
        .isEqualTo(ResponseStatus.CORRECT);
  }

  @Test
  void afterRespondingToLastQuestionShowsResults() {
    QuizController quizController = createAndStartQuizControllerWithOneQuestion();

    final Model model = new ConcurrentModel();

    quizController.askQuestion(model);
    AskQuestionForm askQuestionForm = new AskQuestionForm();
    askQuestionForm.setSelectedChoice("Correct Answer");
    final String redirectPage = quizController.questionResponse(askQuestionForm);

    assertThat(redirectPage)
        .isEqualTo("redirect:/result");
  }

  @Test
  void showsResultOnPage() {
    final QuizController quizController = createAndStartQuizControllerWithOneQuestion();
    final Model model = new ConcurrentModel();
    quizController.showResult(model);

    assertThat(model.containsAttribute("resultView")).isTrue();
  }

  @Test
  void askQuestionTwiceGoesToSamePage() {
    // Given
    final QuizController quizController = createAndStartQuizControllerWithOneQuestion();
    final Model model = new ConcurrentModel();
    quizController.askQuestion(model);

    // When
    final String page = quizController.askQuestion(model);

    // Then
    assertThat(page)
        .isEqualTo("quiz");
  }

  @Test
  void askingQuestionOnAFinishedQuizReturnsResult() {
    final QuizController quizController = createAndStartQuizControllerWithOneQuestion();
    final ConcurrentModel model = new ConcurrentModel();
    quizController.askQuestion(model);
    AskQuestionForm askQuestionForm = new AskQuestionForm();
    askQuestionForm.setSelectedChoice("Correct Answer");
    quizController.questionResponse(askQuestionForm);
    final String redirectPage = quizController.askQuestion(model);

    assertThat(redirectPage)
        .isEqualTo("redirect:/result");
  }


  @Test
  void afterStartCreateSessionAndRedirectToQuiz() {
    // Given
    final QuizController quizController = createAndStartQuizControllerWithOneQuestion();

    // When
    final String redirect = quizController.start();


    // Then
    assertThat(redirect)
        .isEqualTo("redirect:/quiz");
  }

  private QuizController createAndStartQuizControllerWithOneQuestion() {
    Quiz quiz = TestQuizFactory.createQuizWithQuestions(1);
    QuizController quizController = new QuizController(quiz);
    quizController.start();
    return quizController;
  }
}
