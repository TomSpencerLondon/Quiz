package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.hexagon.application.QuizSessionService;
import com.example.quiz.hexagon.application.QuizSessionServiceBuilder;
import com.example.quiz.hexagon.application.port.QuestionRepository;
import com.example.quiz.hexagon.domain.Question;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizControllerTest {

    @Test
    void afterQuizStartedAskForQuestionReturnsFirstQuestion() {
        // given
        QuizSessionServiceBuilder quizSessionServiceBuilder = new QuizSessionServiceBuilder();
        QuizSessionService quizSessionService = quizSessionServiceBuilder
                .withSingleChoiceQuestion()
                .withQuiz(76L)
                .withQuizSession()
                .build();
        QuestionRepository questionRepository = quizSessionServiceBuilder.questionRepository();
        QuizController quizController = new QuizController(quizSessionService, questionRepository);

        quizController.start(76L);

        // when
        final Model model = new ConcurrentModel();
        String templateName = quizController.askQuestion(model, "stub-id-1");

        // then
        assertThat(templateName)
                .isEqualTo("single-choice");
        final AskQuestionForm askQuestion = (AskQuestionForm) model.getAttribute("askQuestionForm");
        assertThat(askQuestion.getQuestion())
                .isEqualTo("Question 1");
    }

    @Test
    void storesFormResponseAnswerInQuizSessionMarkedAsCorrectAnswer() {
        // given
        QuizSessionServiceBuilder quizSessionServiceBuilder = new QuizSessionServiceBuilder();
        QuizSessionService quizSessionService = quizSessionServiceBuilder
                .withSingleChoiceQuestion()
                .withQuiz(73L)
                .withQuizSession()
                .build();
        QuestionRepository questionRepository = quizSessionServiceBuilder.questionRepository();
        QuizController quizController = new QuizController(quizSessionService, questionRepository);
        quizController.start(73L);
        AskQuestionForm askQuestionForm = AskQuestionForm.from(quizSessionServiceBuilder.question());

        // when
        askQuestionForm.setSelectedChoices(quizSessionServiceBuilder.correctChoicesForQuestion());
        quizController.questionResponse(askQuestionForm, "stub-id-1");

        // then
        assertThat(quizSessionService.findSessionByToken("stub-id-1").correctResponsesCount())
                .isEqualTo(1L);
    }

    @Test
    void afterRespondingToLastQuestionShowsResults() {
        // given
        QuizSessionServiceBuilder quizSessionServiceBuilder = new QuizSessionServiceBuilder();
        QuizSessionService quizSessionService = quizSessionServiceBuilder
                .withSingleChoiceQuestion()
                .withQuiz(55L)
                .withQuizSession()
                .build();
        QuestionRepository questionRepository = quizSessionServiceBuilder.questionRepository();
        QuizController quizController = new QuizController(quizSessionService, questionRepository);
        final Model model = new ConcurrentModel();
        quizController.start(55L);
        quizController.askQuestion(model, "stub-id-1");
        Question question = quizSessionServiceBuilder.question();
        AskQuestionForm askQuestionForm = AskQuestionForm.from(question);

        // when
        askQuestionForm.setSelectedChoices(quizSessionServiceBuilder.correctChoicesForQuestion());
        String redirectPage = quizController.questionResponse(askQuestionForm, "stub-id-1");

        // then
        assertThat(redirectPage)
                .isEqualTo("redirect:/result?token=stub-id-1");
    }

    @Test
    void afterQuestionResponseResultViewHasQuestion() {
        QuizSessionServiceBuilder quizSessionServiceBuilder = new QuizSessionServiceBuilder();
        QuizSessionService quizSessionService = quizSessionServiceBuilder
                .withSingleChoiceQuestion()
                .withQuiz(34L)
                .withQuizSession()
                .build();
        QuestionRepository questionRepository = quizSessionServiceBuilder.questionRepository();
        QuizController quizController = new QuizController(quizSessionService, questionRepository);

        final Model model = new ConcurrentModel();
        quizController.start(34L);

        // when
        AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setSelectedChoices(1);
        quizController.questionResponse(askQuestionForm, "stub-id-1");
        quizController.showResult(model, "stub-id-1");

        // then
        ResultView resultView = (ResultView) model.getAttribute("resultView");
        List<ResponseView> responseViews = resultView.getResponsesViews();

        assertThat(responseViews)
                .hasSize(1);
        assertThat(responseViews.get(0).getQuestionView().getText())
                .isEqualTo("Question 1");
    }

    @Test
    void askQuestionTwiceGoesToSamePage() {
        // Given
        QuizSessionServiceBuilder quizSessionServiceBuilder = new QuizSessionServiceBuilder();
        QuizSessionService quizSessionService = quizSessionServiceBuilder
                .withSingleChoiceQuestion()
                .withQuiz(34L)
                .withQuizSession()
                .build();
        QuestionRepository questionRepository = quizSessionServiceBuilder.questionRepository();
        QuizController quizController = new QuizController(quizSessionService, questionRepository);

        quizController.start(34L);
        ConcurrentModel model = new ConcurrentModel();
        quizController.askQuestion(model, "stub-id-1");

        // When
        final String page = quizController.askQuestion(model, "stub-id-1");

        // Then
        assertThat(page)
                .isEqualTo("single-choice");
    }

    @Test
    void askingQuestionOnAFinishedQuizReturnsResult() {
        // given
        QuizSessionServiceBuilder quizSessionServiceBuilder = new QuizSessionServiceBuilder();
        QuizSessionService quizSessionService = quizSessionServiceBuilder
                .withSingleChoiceQuestion()
                .withQuiz(34L)
                .withQuizSession()
                .build();
        QuestionRepository questionRepository = quizSessionServiceBuilder.questionRepository();
        QuizController quizController = new QuizController(quizSessionService, questionRepository);


        Question question = quizSessionServiceBuilder.question();
        AskQuestionForm askQuestionForm = AskQuestionForm.from(question);


        // when
        askQuestionForm.setSelectedChoices(quizSessionServiceBuilder.correctChoicesForQuestion());
        quizController.questionResponse(askQuestionForm, "stub-id-1");

        // then
        String redirectPage = quizController.askQuestion(new ConcurrentModel(), "stub-id-1");
        assertThat(redirectPage)
                .isEqualTo("redirect:/result?token=stub-id-1");
    }


    @Test
    void afterStartCreateSessionAndRedirectToQuiz() {
        // given
        QuizSessionServiceBuilder quizSessionServiceBuilder = new QuizSessionServiceBuilder();
        QuestionRepository questionRepository = quizSessionServiceBuilder.questionRepository();
        QuizSessionService quizSessionService = quizSessionServiceBuilder
                .withSingleChoiceQuestion()
                .withQuiz(34L)
                .build();
        QuizController quizController = new QuizController(quizSessionService, questionRepository);


        // when
        String redirect = quizController.start(34L);

        // then
        assertThat(redirect)
                .isEqualTo("redirect:/question?token=stub-id-1");
    }

    @Test
    void multipleChoiceQuestionReturnsMultipleChoicePage() {
        QuizSessionServiceBuilder quizSessionServiceBuilder = new QuizSessionServiceBuilder();
        QuizSessionService quizSessionService = quizSessionServiceBuilder
                .withMultipleChoiceQuestion()
                .withQuiz(34L)
                .build();
        QuestionRepository questionRepository = quizSessionServiceBuilder.questionRepository();
        QuizController quizController = new QuizController(quizSessionService, questionRepository);
        quizController.start(34L);

        ConcurrentModel model = new ConcurrentModel();
        String redirect = quizController.askQuestion(model, "stub-id-1");

        assertThat(redirect)
                .isEqualTo("multiple-choice");
        assertThat(model.containsAttribute("askQuestionForm"))
                .isTrue();
    }

    @Test
    void whenStartSameQuizTwiceGetTwoDifferentSessionTokens() {
        // given
        QuizSessionServiceBuilder quizSessionServiceBuilder = new QuizSessionServiceBuilder();
        QuestionRepository questionRepository = quizSessionServiceBuilder.questionRepository();
        QuizSessionService quizSessionService = quizSessionServiceBuilder
                .withSingleChoiceQuestion()
                .withQuiz(34L)
                .build();
        QuizController quizController = new QuizController(quizSessionService, questionRepository);

        // when
        String start1 = quizController.start(34L);
        String start2 = quizController.start(34L);

        // then
        assertThat(start1)
                .isEqualTo("redirect:/question?token=stub-id-1");
        assertThat(start2)
                .isEqualTo("redirect:/question?token=stub-id-2");
    }
}