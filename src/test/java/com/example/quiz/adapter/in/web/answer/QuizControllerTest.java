package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.adapter.in.web.QuizControllerTestFactory;
import com.example.quiz.domain.Quiz;
import com.example.quiz.domain.QuizSession;
import com.example.quiz.domain.factories.QuizTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizControllerTest {

    @Test
    void afterQuizStartedAskForQuestionReturnsFirstQuestion() {
        QuizController quizController = QuizControllerTestFactory.createAndStartQuizControllerWithOneSingleChoiceQuestion();
        final Model model = new ConcurrentModel();

        quizController.askQuestion(model);
        final AskQuestionForm askQuestion = (AskQuestionForm) model.getAttribute("askQuestionForm");

        assertThat(askQuestion.getQuestion())
                .isEqualTo("Question 1");
    }

    @Test
    void storesFormResponseAnswerInQuizSessionMarkedAsCorrectAnswer() {
        Quiz quiz = QuizTestFactory.createQuizWithSingleChoiceQuestion();
        QuizSession quizSession = quiz.start();
        QuizController quizController = new QuizController(quizSession);
        final Model model = new ConcurrentModel();

        quizController.askQuestion(model);
        AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setSelectedChoices(0);
        quizController.questionResponse(askQuestionForm);

        assertThat(quizSession.correctResponsesCount())
                .isEqualTo(1L);
    }

    @Test
    void afterRespondingToLastQuestionShowsResults() {
        QuizController quizController = QuizControllerTestFactory.createAndStartQuizControllerWithOneSingleChoiceQuestion();
        final Model model = new ConcurrentModel();

        quizController.askQuestion(model);
        AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setSelectedChoices(0);
        final String redirectPage = quizController.questionResponse(askQuestionForm);

        assertThat(redirectPage)
                .isEqualTo("redirect:/result");
    }

    @Test
    void showsResultOnPage() {
        final QuizController quizController = QuizControllerTestFactory.createAndStartQuizControllerWithOneSingleChoiceQuestion();
        final Model model = new ConcurrentModel();

        quizController.showResult(model);

        assertThat(model.containsAttribute("resultView")).isTrue();
    }

    @Test
    void askQuestionTwiceGoesToSamePage() {
        // Given
        final QuizController quizController = QuizControllerTestFactory.createAndStartQuizControllerWithOneSingleChoiceQuestion();

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
        QuizController quizController = QuizControllerTestFactory.createAndStartQuizControllerWithOneSingleChoiceQuestion();
        ConcurrentModel model = new ConcurrentModel();

        quizController.askQuestion(model);
        AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setSelectedChoices(0);
        quizController.questionResponse(askQuestionForm);
        String redirectPage = quizController.askQuestion(model);

        assertThat(redirectPage)
                .isEqualTo("redirect:/result");
    }


    @Test
    void afterStartCreateSessionAndRedirectToQuiz() {
        QuizController quizController = QuizControllerTestFactory.createAndStartQuizControllerWithOneSingleChoiceQuestion();
        String redirect = quizController.start();
        assertThat(redirect)
                .isEqualTo("redirect:/quiz");
    }

    @Test
    void multipleChoiceQuestionReturnsMultipleChoicePage() {
        QuizController quizController = QuizControllerTestFactory.createAndStartChoiceQuizControllerWithOneMultipleChoiceQuestion();
        ConcurrentModel model = new ConcurrentModel();

        String redirect = quizController.askQuestion(model);

        assertThat(redirect)
                .isEqualTo("multiple-choice");
        assertThat(model.containsAttribute("askQuestionForm"))
                .isTrue();
    }

}
