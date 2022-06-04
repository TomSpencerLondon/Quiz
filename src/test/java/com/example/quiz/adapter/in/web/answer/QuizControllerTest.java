package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.adapter.in.web.QuizControllerTestFactory;
import com.example.quiz.application.QuizService;
import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.port.*;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Quiz;
import com.example.quiz.domain.QuizId;
import com.example.quiz.domain.QuizSession;
import com.example.quiz.domain.factories.MultipleChoiceQuestionTestFactory;
import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizControllerTest {

    public static final QuestionRepository DUMMY_QUESTION_REPOSITORY = null;

    @Test
    void afterQuizStartedAskForQuestionReturnsFirstQuestion() {
        // given
        QuizController quizController = QuizControllerTestFactory.createAndStartQuizControllerWithOneSingleChoiceQuestion();
        quizController.start(0L);

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
        StubQuestionRepository stubQuestionRepository = new StubQuestionRepository();
        Question singleChoiceQuestion = stubQuestionRepository.findAll().get(0);
        QuizService quizService = new QuizService(stubQuestionRepository);
        StubTokenGenerator stubIdGenerator = new StubTokenGenerator();
        QuizSessionService quizSessionService =
                new QuizSessionService(
                        new InMemoryQuizSessionRepository(),
                        QuizControllerTestFactory.createQuizRepositoryWithOneQuizWith(singleChoiceQuestion),
                        stubIdGenerator);

        QuizController quizController = new QuizController(quizSessionService, stubQuestionRepository);
        quizController.start(0L);
        AskQuestionForm askQuestionForm = AskQuestionForm.from(singleChoiceQuestion);

        // when
        long selectedChoiceId = singleChoiceQuestion.choices().get(0).getId().id();
        askQuestionForm.setSelectedChoices(selectedChoiceId);
        quizController.questionResponse(askQuestionForm, "stub-id-1");

        // then
        assertThat(quizSessionService.findSessionByToken("stub-id-1").correctResponsesCount())
                .isEqualTo(1L);
    }

    @Test
    void afterRespondingToLastQuestionShowsResults() {
        // given
        StubQuestionRepository stubQuestionRepository = new StubQuestionRepository();
        Question singleChoiceQuestion = stubQuestionRepository.findAll().get(0);
        QuizService quizService = new QuizService(stubQuestionRepository);
        QuizRepository quizRepository = QuizControllerTestFactory.createQuizRepositoryWithOneQuizWith(singleChoiceQuestion);
        StubTokenGenerator stubIdGenerator = new StubTokenGenerator();
        QuizSessionService quizSessionService = new QuizSessionService(new InMemoryQuizSessionRepository(), quizRepository, stubIdGenerator);
        QuizController quizController = new QuizController(quizSessionService, stubQuestionRepository);
        final Model model = new ConcurrentModel();
        quizController.start(0L);
        quizController.askQuestion(model, "stub-id-1");
        AskQuestionForm askQuestionForm = AskQuestionForm.from(singleChoiceQuestion);

        // when
        long selectedChoiceId = singleChoiceQuestion.choices().get(1).getId().id();
        askQuestionForm.setSelectedChoices(selectedChoiceId);
        String redirectPage = quizController.questionResponse(askQuestionForm, "stub-id-1");

        // then
        assertThat(redirectPage)
                .isEqualTo("redirect:/result?token=stub-id-1");
    }

    @Test
    void afterQuestionResponseResultViewHasQuestion() {
        // given
        final QuizController quizController = QuizControllerTestFactory.createAndStartQuizControllerWithOneSingleChoiceQuestion();
        final Model model = new ConcurrentModel();
        quizController.start(0L);

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
        final QuizController quizController = QuizControllerTestFactory.createAndStartQuizControllerWithOneSingleChoiceQuestion();
        final Model model = new ConcurrentModel();
        quizController.start(0L);
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
        StubQuestionRepository stubQuestionRepository = new StubQuestionRepository();
        Question singleChoiceQuestion = stubQuestionRepository.findAll().get(0);
        QuizService quizService = new QuizService(stubQuestionRepository);
        InMemoryQuizRepository quizRepository = new InMemoryQuizRepository();
        Quiz quiz = new Quiz("Quiz 1", List.of(singleChoiceQuestion.getId()));
        quizRepository.save(quiz);
        StubTokenGenerator stubIdGenerator = new StubTokenGenerator();
        QuizSessionService quizSessionService = new QuizSessionService(new InMemoryQuizSessionRepository(), quizRepository, stubIdGenerator);
        QuizController quizController = new QuizController(quizSessionService, stubQuestionRepository);
        ConcurrentModel model = new ConcurrentModel();
        quizController.start(0L);
        quizController.askQuestion(model, "stub-id-1");
        AskQuestionForm askQuestionForm = AskQuestionForm.from(singleChoiceQuestion);


        // when
        long selectedChoiceId = singleChoiceQuestion.choices().get(1).getId().id();
        askQuestionForm.setSelectedChoices(selectedChoiceId);
        quizController.questionResponse(askQuestionForm, "stub-id-1");

        // then
        String redirectPage = quizController.askQuestion(model, "stub-id-1");
        assertThat(redirectPage)
                .isEqualTo("redirect:/result?token=stub-id-1");
    }


    @Test
    void afterStartCreateSessionAndRedirectToQuiz() {
        // given
        QuizController quizController = QuizControllerTestFactory.createAndStartQuizControllerWithOneSingleChoiceQuestion();

        // when
        String redirect = quizController.start(0L);

        // then
        assertThat(redirect)
                .isEqualTo("redirect:/question?token=stub-id-1");
    }

    @Test
    void multipleChoiceQuestionReturnsMultipleChoicePage() {
        QuizController quizController = QuizControllerTestFactory.createAndStartChoiceQuizControllerWithOneMultipleChoiceQuestion();
        quizController.start(0L);

        ConcurrentModel model = new ConcurrentModel();
        String redirect = quizController.askQuestion(model, "stub-id-1");

        assertThat(redirect)
                .isEqualTo("multiple-choice");
        assertThat(model.containsAttribute("askQuestionForm"))
                .isTrue();
    }

    @Test
    void askQuestionPullsQuestionFromSessionBasedOnId() {
        // given
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        Question savedQuestion = questionRepository.save(SingleChoiceQuestionTestFactory.createSingleChoiceQuestion());
        QuizService quizService = new QuizService(questionRepository);
        StubTokenGenerator stubIdGenerator = new StubTokenGenerator();
        QuizSessionService quizSessionService = new QuizSessionService(new InMemoryQuizSessionRepository(), QuizControllerTestFactory.createQuizRepositoryWithOneQuizWith(savedQuestion), stubIdGenerator);
        QuizController quizController = new QuizController(quizSessionService, questionRepository);

        // when
        String start1 = quizController.start(0L);
        String start2 = quizController.start(0L);

        // then
        assertThat(start1)
                .isEqualTo("redirect:/question?token=stub-id-1");
        assertThat(start2)
                .isEqualTo("redirect:/question?token=stub-id-2");
    }

    @Test
    void startWithQuizIdCreatesSessionWithSameQuizId() {
        Question multipleChoiceQuestion = MultipleChoiceQuestionTestFactory.multipleChoiceQuestion();
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        Question savedQuestion = questionRepository.save(multipleChoiceQuestion);
        QuizService quizService = new QuizService(questionRepository);
        QuizRepository quizRepository = new InMemoryQuizRepository();
        quizRepository.save(
                new Quiz("Test Quiz", List.of(savedQuestion.getId())));
        StubTokenGenerator stubIdGenerator = new StubTokenGenerator();
        QuizSessionService quizSessionService = createQuizSessionService(quizRepository, stubIdGenerator);
        QuizController quizController = createQuizController(questionRepository, stubIdGenerator, quizSessionService);
        // Objects needed for executing and asserting
        // 1. QuizController
        // 2. QuizSessionService
        // 3. Quiz Repository

        QuizId savedQuizId = quizRepository.findAll().get(0).getId();
        quizController.start(savedQuizId.id());

        QuizSession sessionByToken = quizSessionService.findSessionByToken("stub-id-1");
        assertThat(sessionByToken.quizId())
                .isEqualTo(savedQuizId);
    }

    @NotNull
    private QuizSessionService createQuizSessionService(QuizRepository quizRepository, StubTokenGenerator stubIdGenerator) {
        QuizSessionService quizSessionService =
                new QuizSessionService(
                        new InMemoryQuizSessionRepository(),
                        quizRepository,
                        stubIdGenerator);
        return quizSessionService;
    }

    @NotNull
    private QuizController createQuizController(QuestionRepository questionRepository, StubTokenGenerator stubIdGenerator, QuizSessionService quizSessionService) {
        QuizController quizController =
                new QuizController(quizSessionService, questionRepository);
        return quizController;
    }
}