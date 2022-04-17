package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.application.QuizService;
import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.QuizSessionServiceTestFactory;
import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.InMemoryQuizSessionRepository;
import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.application.port.TokenGenerator;
import com.example.quiz.domain.FinishedQuizSession;
import com.example.quiz.domain.Grade;
import com.example.quiz.domain.QuizSession;
import com.example.quiz.domain.UnfinishedQuizSession;
import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizControllerIdTest {

    public static final QuestionRepository DUMMY_QUESTION_REPOSITORY = null;
    public static final TokenGenerator DUMMY_TOKEN_GENERATOR = null;

    @Test
    void askQuestionWithoutIdThenRedirectsToStart() {
        // given
        QuizSessionService quizSessionService = QuizSessionServiceTestFactory.createQuizSessionService();
        QuizController quizController = new QuizController(quizSessionService, new StubTokenGenerator(), DUMMY_QUESTION_REPOSITORY);
        quizController.start();
        final Model model = new ConcurrentModel();

        // when
        String redirectPage = quizController.askQuestion(model, "");

        // then
        assertThat(redirectPage)
                .isEqualTo("redirect:/");
    }

    @Test
    void answerQuestionForSingleQuizSessionAddsResponse() {
        // given
        QuizSessionService quizSessionService = QuizSessionServiceTestFactory.createQuizSessionService();
        QuizController quizController = new QuizController(quizSessionService, new StubTokenGenerator(), DUMMY_QUESTION_REPOSITORY);
        quizController.start();
        quizController.askQuestion(new ConcurrentModel(), "stub-id-1");
        AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setQuestion(SingleChoiceQuestionTestFactory.createSingleChoiceQuestion().text());
        askQuestionForm.setChoices(List.of(new ChoiceSelection(1, "true"), new ChoiceSelection(2, "false")));
        askQuestionForm.setSelectedChoices(0);

        // when
        quizController.questionResponse(askQuestionForm, "stub-id-1");

        // then
        QuizSession sessionById = quizSessionService.findSessionByToken("stub-id-1");
        boolean finished = sessionById.isFinished();
        Grade grade = sessionById.grade();
        assertThat(finished)
                .isTrue();
        assertThat(grade.percent())
                .isEqualTo(100);
    }

    @Test
    void answerQuestionForFirstOfTwoSessionsAddsResponseToFirstSession() {
        // given
        QuizSessionService quizSessionService = QuizSessionServiceTestFactory.createQuizSessionService();
        QuizController quizController = new QuizController(quizSessionService, new StubTokenGenerator(), DUMMY_QUESTION_REPOSITORY);
        quizController.start();
        quizController.start();
        quizController.askQuestion(new ConcurrentModel(), "stub-id-1");
        AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setQuestion(SingleChoiceQuestionTestFactory.createSingleChoiceQuestion().text());
        askQuestionForm.setChoices(List.of(new ChoiceSelection(1, "true"), new ChoiceSelection(2, "false")));
        askQuestionForm.setSelectedChoices(0);

        // when
        quizController.questionResponse(askQuestionForm, "stub-id-1");

        // then
        QuizSession session1 = quizSessionService.findSessionByToken("stub-id-1");
        boolean finished = session1.isFinished();
        Grade grade = session1.grade();
        assertThat(finished)
                .isTrue();
        assertThat(grade.percent())
                .isEqualTo(100);

        QuizSession session2 = quizSessionService.findSessionByToken("stub-id-2");
        boolean finished2 = session2.isFinished();
        Grade grade2 = session2.grade();
        assertThat(finished2)
                .isFalse();
        assertThat(grade2.percent())
                .isEqualTo(0);
    }

    @Test
    void askQuestionRedirectsToResultForTheFinishedSession() {
        // given
        InMemoryQuizSessionRepository quizSessionRepository = new InMemoryQuizSessionRepository();
        quizSessionRepository.save(new FinishedQuizSession("finished"));
        quizSessionRepository.save(new UnfinishedQuizSession("unfinished"));
        QuizService quizService = new QuizService(new InMemoryQuestionRepository());
        QuizSessionService quizSessionService = new QuizSessionService(quizService, quizSessionRepository);

        QuizController quizController = new QuizController(quizSessionService, DUMMY_TOKEN_GENERATOR, DUMMY_QUESTION_REPOSITORY);

        // when
        String redirect = quizController.askQuestion(new ConcurrentModel(), "finished");

        // then
        assertThat(redirect)
                .isEqualTo("redirect:/result?token=finished");
    }



}
