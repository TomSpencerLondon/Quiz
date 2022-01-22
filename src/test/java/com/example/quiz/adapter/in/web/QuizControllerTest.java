package com.example.quiz.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.domain.Choice;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Quiz;
import com.example.quiz.domain.QuizSession;
import com.example.quiz.domain.SingleChoice;
import com.example.quiz.domain.SingleChoiceQuestionTestFactory;
import com.example.quiz.domain.TestQuizFactory;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

public class QuizControllerTest {

  @Test
  void afterQuizStartedAskForQuestionReturnsFirstQuestion() {
    QuestionRepository questionRepository = new InMemoryQuestionRepository();
    questionRepository.save(SingleChoiceQuestionTestFactory.createSingleChoiceQuestion());
    Quiz quiz = new Quiz(questionRepository);
    QuizController quizController = new QuizController(quiz);
    quizController.start();

    final Model model = new ConcurrentModel();

    quizController.askQuestion(model);

    final AskSingleChoiceQuestionForm question = (AskSingleChoiceQuestionForm) model.getAttribute("askSingleChoiceQuestionForm");

    assertThat(question.getQuestion())
        .isEqualTo("Question 1");
  }

  @Test
  void storesFormResponseAnswerInQuizSessionMarkedAsCorrectAnswer() {
    QuestionRepository questionRepository = new InMemoryQuestionRepository();
    Question question = new Question(
        "Question 1",
        new SingleChoice(new Choice("Correct Answer"),
            List.of(new Choice("Correct Answer"), new Choice("Wrong Answer"))));
    questionRepository.save(question);

    Quiz quiz = new Quiz(questionRepository);
    QuizSession quizSession = new QuizSession(quiz);
    QuizController quizController = new QuizController(quizSession);
    final Model model = new ConcurrentModel();
    quizController.askQuestion(model);

    AskSingleChoiceQuestionForm askSingleChoiceQuestionForm = new AskSingleChoiceQuestionForm();
    askSingleChoiceQuestionForm.setSelectedChoice("Correct Answer");
    quizController.questionResponse(askSingleChoiceQuestionForm);

    assertThat(quizSession.correctResponsesCount())
        .isEqualTo(1L);
  }

  @Test
  void afterRespondingToLastQuestionShowsResults() {
    QuizController quizController = createAndStartQuizControllerWithOneSingleChoiceQuestion();

    final Model model = new ConcurrentModel();

    quizController.askQuestion(model);
    AskSingleChoiceQuestionForm askSingleChoiceQuestionForm = new AskSingleChoiceQuestionForm();
    askSingleChoiceQuestionForm.setSelectedChoice("Correct Answer");
    final String redirectPage = quizController.questionResponse(askSingleChoiceQuestionForm);

    assertThat(redirectPage)
        .isEqualTo("redirect:/result");
  }

  @Test
  void showsResultOnPage() {
    final QuizController quizController = createAndStartQuizControllerWithOneSingleChoiceQuestion();
    final Model model = new ConcurrentModel();
    quizController.showResult(model);

    assertThat(model.containsAttribute("resultView")).isTrue();
  }

  @Test
  void askQuestionTwiceGoesToSamePage() {
    // Given
    final QuizController quizController = createAndStartQuizControllerWithOneSingleChoiceQuestion();
    final Model model = new ConcurrentModel();
    quizController.askQuestion(model);

    // When
    final String page = quizController.askQuestion(model);

    // Then
    assertThat(page)
        .isEqualTo("single-choice");
  }

  @Test
  void askingQuestionOnAFinishedQuizReturnsResult() {
    final QuizController quizController = createAndStartQuizControllerWithOneSingleChoiceQuestion();
    final ConcurrentModel model = new ConcurrentModel();
    quizController.askQuestion(model);
    AskSingleChoiceQuestionForm askSingleChoiceQuestionForm = new AskSingleChoiceQuestionForm();
    askSingleChoiceQuestionForm.setSelectedChoice("Correct Answer");
    quizController.questionResponse(askSingleChoiceQuestionForm);
    final String redirectPage = quizController.askQuestion(model);

    assertThat(redirectPage)
        .isEqualTo("redirect:/result");
  }


  @Test
  void afterStartCreateSessionAndRedirectToQuiz() {
    // Given
    final QuizController quizController = createAndStartQuizControllerWithOneSingleChoiceQuestion();

    // When
    final String redirect = quizController.start();


    // Then
    assertThat(redirect)
        .isEqualTo("redirect:/quiz");
  }

  @Test
  void multipleChoiceQuestionReturnsMultipleChoicePage() {
    final InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
    final List<Choice> choices = List.of(new Choice("Answer 1"), new Choice("Answer 2"),
        new Choice("Answer 3"), new Choice("Answer 4"));

    MultipleChoice multipleChoice = new MultipleChoice(choices);

    inMemoryQuestionRepository.save(new Question("Question 1", multipleChoice));

    final Quiz quiz = new Quiz(inMemoryQuestionRepository);

    final QuizController quizController = new QuizController(quiz);
    quizController.start();
    final ConcurrentModel model = new ConcurrentModel();

    final String redirect = quizController.askQuestion(model);

    assertThat(redirect)
        .isEqualTo("multiple-choice");
  }

  private QuizController createAndStartQuizControllerWithOneSingleChoiceQuestion() {
    Quiz quiz = TestQuizFactory.createQuizWithSingleChoiceQuestions(1);
    QuizController quizController = new QuizController(quiz);
    quizController.start();
    return quizController;
  }
}
