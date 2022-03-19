package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.QuizSessionServiceTestFactory;
import com.example.quiz.domain.Grade;
import com.example.quiz.domain.QuizSession;
import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizControllerIdTest {
    @Test
    void askQuestionWithoutIdThenRedirectsToStart() {
        QuizSessionService quizSessionService = QuizSessionServiceTestFactory.createQuizSessionService();
        QuizController quizController = new QuizController(quizSessionService, new StubIdGenerator());
        quizController.start();

        final Model model = new ConcurrentModel();
        String redirectPage = quizController.askQuestion(model, "");
        assertThat(redirectPage)
                .isEqualTo("redirect:/start");
    }

    @Test
    void answerQuestionWithoutIdThenRedirectsToStart() {
        QuizSessionService quizSessionService = QuizSessionServiceTestFactory.createQuizSessionService();
        QuizController quizController = new QuizController(quizSessionService, new StubIdGenerator());
        quizController.start();
        AskQuestionForm askQuestionForm = new AskQuestionForm();
        String redirectPage = quizController.questionResponse(askQuestionForm, "");

        assertThat(redirectPage)
                .isEqualTo("redirect:/start");

    }


    @Test
    void answerQuestionForSingleQuizSessionAddsResponse() {
        QuizSessionService quizSessionService = QuizSessionServiceTestFactory.createQuizSessionService();
        QuizController quizController = new QuizController(quizSessionService, new StubIdGenerator());
        quizController.start();
        quizController.askQuestion(new ConcurrentModel(), "stub-id-1");
        AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setQuestion(SingleChoiceQuestionTestFactory.createSingleChoiceQuestion().text());
        askQuestionForm.setChoices(List.of(new ChoiceSelection(1, "true"), new ChoiceSelection(2, "false")));
        askQuestionForm.setSelectedChoices(0);
        quizController.questionResponse(askQuestionForm, "stub-id-1");
        QuizSession sessionById = quizSessionService.findSessionById("stub-id-1");
        boolean finished = sessionById.isFinished();
        Grade grade = sessionById.grade();
        assertThat(finished)
                .isTrue();
        assertThat(grade.percent())
                .isEqualTo(100);
    }

    @Test
    void answerQuestionForFirstOfTwoSessionsAddsResponseToFirstSession() {
        QuizSessionService quizSessionService = QuizSessionServiceTestFactory.createQuizSessionService();
        QuizController quizController = new QuizController(quizSessionService, new StubIdGenerator());
        quizController.start();
        quizController.start();
        quizController.askQuestion(new ConcurrentModel(), "stub-id-1");
        AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setQuestion(SingleChoiceQuestionTestFactory.createSingleChoiceQuestion().text());
        askQuestionForm.setChoices(List.of(new ChoiceSelection(1, "true"), new ChoiceSelection(2, "false")));
        askQuestionForm.setSelectedChoices(0);
        quizController.questionResponse(askQuestionForm, "stub-id-1");

        QuizSession session1 = quizSessionService.findSessionById("stub-id-1");
        boolean finished = session1.isFinished();
        Grade grade = session1.grade();
        assertThat(finished)
                .isTrue();
        assertThat(grade.percent())
                .isEqualTo(100);

        QuizSession session2 = quizSessionService.findSessionById("stub-id-2");
        boolean finished2 = session2.isFinished();
        Grade grade2 = session2.grade();
        assertThat(finished2)
                .isFalse();
        assertThat(grade2.percent())
                .isEqualTo(0);
    }
}
