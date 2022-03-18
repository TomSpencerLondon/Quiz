package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.adapter.in.web.QuizControllerTestFactory;
import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.QuizSessionServiceTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizControllerTest {

    @Test
    void afterQuizStartedAskForQuestionReturnsFirstQuestion() {
        QuizController quizController = QuizControllerTestFactory.createAndStartQuizControllerWithOneSingleChoiceQuestion();
        final Model model = new ConcurrentModel();

        quizController.askQuestion(model, "stub-id-1");
        final AskQuestionForm askQuestion = (AskQuestionForm) model.getAttribute("askQuestionForm");

        assertThat(askQuestion.getQuestion())
                .isEqualTo("Question 1");
    }

    @Test
    void storesFormResponseAnswerInQuizSessionMarkedAsCorrectAnswer() {
        QuizSessionService quizSessionService = QuizSessionServiceTestFactory.createQuizSessionService();
        StubIdGenerator stubIdGenerator = new StubIdGenerator();
        QuizController quizController = new QuizController(quizSessionService, stubIdGenerator);
        quizController.start("stub-id-1");

        final Model model = new ConcurrentModel();

        quizController.askQuestion(model, "stub-id-1");
        AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setSelectedChoices(0);
        quizController.questionResponse(askQuestionForm, "stub-id-1");

        assertThat(quizSessionService.findSessionById("stub-id-1").correctResponsesCount())
                .isEqualTo(1L);
    }

    @Test
    void afterRespondingToLastQuestionShowsResults() {
        QuizController quizController = QuizControllerTestFactory.createAndStartQuizControllerWithOneSingleChoiceQuestion();
        final Model model = new ConcurrentModel();

        quizController.askQuestion(model, "stub-id-1");
        AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setSelectedChoices(0);
        final String redirectPage = quizController.questionResponse(askQuestionForm, "stub-id-1");

        assertThat(redirectPage)
                .isEqualTo("redirect:/result");
    }

    @Test
    void showsResultOnPage() {
        final QuizController quizController = QuizControllerTestFactory.createAndStartQuizControllerWithOneSingleChoiceQuestion();
        final Model model = new ConcurrentModel();

        quizController.showResult(model, "stub-id-1");

        assertThat(model.containsAttribute("resultView")).isTrue();
    }

    @Test
    void askQuestionTwiceGoesToSamePage() {
        // Given
        final QuizController quizController = QuizControllerTestFactory.createAndStartQuizControllerWithOneSingleChoiceQuestion();

        final Model model = new ConcurrentModel();
        quizController.askQuestion(model, "stub-id-1");

        // When
        final String page = quizController.askQuestion(model, "stub-id-1");

        // Then
        assertThat(page)
                .isEqualTo("single-choice");
    }

    @Test
    void askingQuestionOnAFinishedQuizReturnsResult() {
        QuizController quizController = QuizControllerTestFactory.createAndStartQuizControllerWithOneSingleChoiceQuestion();
        ConcurrentModel model = new ConcurrentModel();

        quizController.askQuestion(model, "stub-id-1");
        AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setSelectedChoices(0);
        quizController.questionResponse(askQuestionForm, "stub-id-1");
        String redirectPage = quizController.askQuestion(model, "stub-id-1");

        assertThat(redirectPage)
                .isEqualTo("redirect:/result");
    }


    @Test
    void afterStartCreateSessionAndRedirectToQuiz() {
        QuizController quizController = QuizControllerTestFactory.createAndStartQuizControllerWithOneSingleChoiceQuestion();
        String redirect = quizController.start("stub-id-1");
        assertThat(redirect)
                .isEqualTo("redirect:/quiz");
    }

    @Test
    void multipleChoiceQuestionReturnsMultipleChoicePage() {
        QuizController quizController = QuizControllerTestFactory.createAndStartChoiceQuizControllerWithOneMultipleChoiceQuestion();
        ConcurrentModel model = new ConcurrentModel();

        String redirect = quizController.askQuestion(model, "stub-id-1");

        assertThat(redirect)
                .isEqualTo("multiple-choice");
        assertThat(model.containsAttribute("askQuestionForm"))
                .isTrue();
    }

}
