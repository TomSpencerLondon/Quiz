package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.QuizSessionServiceBuilder;
import com.example.quiz.application.port.*;
import com.example.quiz.domain.*;
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
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder.withQuestionId(1L).withDefaultSingleChoice().save();
        QuestionRepository questionRepository = questionBuilder.questionRepository();
        QuizBuilder quizBuilder = new QuizBuilder();
        quizBuilder.withQuestions(question).save();
        QuizRepository quizRepository = quizBuilder.quizRepository();
        StubTokenGenerator stubIdGenerator = new StubTokenGenerator();

        QuizSessionService quizSessionService =
                new QuizSessionService(
                        new InMemoryQuizSessionRepository(),
                        quizRepository,
                        stubIdGenerator);
        QuizController quizController = new QuizController(quizSessionService, questionRepository);
        quizController.start(1L);

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
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder.withDefaultSingleChoice().save();
        QuestionRepository questionRepository = questionBuilder.questionRepository();
        QuizBuilder quizBuilder = new QuizBuilder();
        quizBuilder.withQuestions(question).save();
        QuizRepository quizRepository = quizBuilder.quizRepository();
        QuizSessionService quizSessionService = new QuizSessionService(
                        new InMemoryQuizSessionRepository(),
                        quizRepository,
                        new StubTokenGenerator());
        QuizController quizController = new QuizController(quizSessionService, questionRepository);
        quizController.start(1L);
        AskQuestionForm askQuestionForm = AskQuestionForm.from(question);

        // when
        askQuestionForm.setSelectedChoices(questionBuilder.correctChoiceForQuestion().id());
        quizController.questionResponse(askQuestionForm, "stub-id-1");

        // then
        assertThat(quizSessionService.findSessionByToken("stub-id-1").correctResponsesCount())
                .isEqualTo(1L);
    }

    @Test
    void afterRespondingToLastQuestionShowsResults() {
        // given
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder.withDefaultSingleChoice().save();
        QuestionRepository questionRepository = questionBuilder.questionRepository();
        QuizBuilder quizBuilder = new QuizBuilder();
        quizBuilder.withQuestions(question).save();
        QuizRepository quizRepository = quizBuilder.quizRepository();

        StubTokenGenerator stubIdGenerator = new StubTokenGenerator();
        QuizSessionService quizSessionService = new QuizSessionService(new InMemoryQuizSessionRepository(), quizRepository, stubIdGenerator);
        QuizController quizController = new QuizController(quizSessionService, questionRepository);
        final Model model = new ConcurrentModel();
        quizController.start(1L);
        quizController.askQuestion(model, "stub-id-1");
        AskQuestionForm askQuestionForm = AskQuestionForm.from(question);

        // when
        askQuestionForm.setSelectedChoices(questionBuilder.correctChoiceForQuestion().id());
        String redirectPage = quizController.questionResponse(askQuestionForm, "stub-id-1");

        // then
        assertThat(redirectPage)
                .isEqualTo("redirect:/result?token=stub-id-1");
    }

    @Test
    void afterQuestionResponseResultViewHasQuestion() {
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder.withQuestionId(1L).withDefaultSingleChoice().save();
        QuestionRepository questionRepository = questionBuilder.questionRepository();
        QuizBuilder quizBuilder = new QuizBuilder();
        Quiz quiz = quizBuilder.withQuestions(question).save();
        QuizRepository quizRepository = quizBuilder.quizRepository();
        QuizSessionService quizSessionService =
                new QuizSessionService(
                        new InMemoryQuizSessionRepository(),
                        quizRepository,
                        new StubTokenGenerator());
        final QuizController quizController = new QuizController(quizSessionService, questionRepository);
        final Model model = new ConcurrentModel();
        quizController.start(quiz.getId().id());

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
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder.withQuestionId(1L).withDefaultSingleChoice().save();
        QuestionRepository questionRepository = questionBuilder.questionRepository();
        QuizBuilder quizBuilder = new QuizBuilder();
        Quiz quiz = quizBuilder.withQuestions(question).save();
        QuizRepository quizRepository = quizBuilder.quizRepository();

        QuizSessionService quizSessionService =
                new QuizSessionService(
                        new InMemoryQuizSessionRepository(),
                        quizRepository,
                        new StubTokenGenerator());
        final QuizController quizController = new QuizController(quizSessionService, questionRepository);
        final Model model = new ConcurrentModel();
        quizController.start(quiz.getId().id());
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
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder.withDefaultSingleChoice().save();
        QuestionRepository questionRepository = questionBuilder.questionRepository();
        QuizBuilder quizBuilder = new QuizBuilder();
        Quiz quiz = quizBuilder.withQuestions(question).save();
        QuizRepository quizRepository = quizBuilder.quizRepository();

        QuizSessionService quizSessionService = new QuizSessionService(new InMemoryQuizSessionRepository(), quizRepository, new StubTokenGenerator());
        QuizController quizController = new QuizController(quizSessionService, questionRepository);
        ConcurrentModel model = new ConcurrentModel();
        quizController.start(quiz.getId().id());
        quizController.askQuestion(model, "stub-id-1");
        AskQuestionForm askQuestionForm = AskQuestionForm.from(question);


        // when
        askQuestionForm.setSelectedChoices(questionBuilder.correctChoiceForQuestion().id());
        quizController.questionResponse(askQuestionForm, "stub-id-1");

        // then
        String redirectPage = quizController.askQuestion(model, "stub-id-1");
        assertThat(redirectPage)
                .isEqualTo("redirect:/result?token=stub-id-1");
    }


    @Test
    void afterStartCreateSessionAndRedirectToQuiz() {
        // given
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder.withDefaultSingleChoice().save();
        QuestionRepository questionRepository = questionBuilder.questionRepository();
        QuizBuilder quizBuilder = new QuizBuilder();
        Quiz quiz = quizBuilder.withQuestions(question).save();
        QuizRepository quizRepository = quizBuilder.quizRepository();

        QuizSessionService quizSessionService =
                new QuizSessionService(
                        new InMemoryQuizSessionRepository(),
                        quizRepository,
                        new StubTokenGenerator());
        QuizController quizController = new QuizController(quizSessionService, questionRepository);

        // when
        String redirect = quizController.start(quiz.getId().id());

        // then
        assertThat(redirect)
                .isEqualTo("redirect:/question?token=stub-id-1");
    }

    @Test
    void multipleChoiceQuestionReturnsMultipleChoicePage() {
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder.withDefaultMultipleChoice().save();
        QuestionRepository questionRepository = questionBuilder.questionRepository();
        QuizBuilder quizBuilder = new QuizBuilder();
        Quiz quiz = quizBuilder.withQuestions(question).save();
        QuizRepository quizRepository = quizBuilder.quizRepository();

        QuizSessionService quizSessionService = new QuizSessionService(new InMemoryQuizSessionRepository(), quizRepository, new StubTokenGenerator());
        QuizController quizController = new QuizController(quizSessionService, questionRepository);
        quizController.start(quiz.getId().id());

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
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder.withDefaultSingleChoice().save();
        QuestionRepository questionRepository = questionBuilder.questionRepository();
        QuizBuilder quizBuilder = new QuizBuilder();
        Quiz quiz = quizBuilder.withQuestions(question).save();
        QuizRepository quizRepository = quizBuilder.quizRepository();
        QuizSessionService quizSessionService = new QuizSessionService(new InMemoryQuizSessionRepository(), quizRepository, new StubTokenGenerator());
        QuizController quizController = new QuizController(quizSessionService, questionRepository);

        // when
        String start1 = quizController.start(quiz.getId().id());
        String start2 = quizController.start(quiz.getId().id());

        // then
        assertThat(start1)
                .isEqualTo("redirect:/question?token=stub-id-1");
        assertThat(start2)
                .isEqualTo("redirect:/question?token=stub-id-2");
    }

    @Test
    void startWithQuizIdCreatesSessionWithSameQuizId() {
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder.withDefaultMultipleChoice().save();
        QuestionRepository questionRepository = questionBuilder.questionRepository();
        QuizBuilder quizBuilder = new QuizBuilder();
        Quiz quiz = quizBuilder.withQuestions(question).save();
        QuizRepository quizRepository = quizBuilder.quizRepository();

        QuizSessionService quizSessionService = new QuizSessionServiceBuilder().withQuizRepository(quizRepository).build();
        QuizController quizController = new QuizController(quizSessionService, questionRepository);
        QuizId savedQuizId = quiz.getId();
        quizController.start(savedQuizId.id());

        QuizSession sessionByToken = quizSessionService.findSessionByToken("stub-id-1");
        assertThat(sessionByToken.quizId())
                .isEqualTo(savedQuizId);
    }
}