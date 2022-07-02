package com.tomspencerlondon.quiz.adapter.in.web.answer;

import com.tomspencerlondon.quiz.hexagon.application.QuizSessionService;
import com.tomspencerlondon.quiz.hexagon.application.QuizSessionServiceBuilder;
import com.tomspencerlondon.quiz.hexagon.application.port.QuestionRepository;
import com.tomspencerlondon.quiz.hexagon.domain.QuizSession;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizControllerIdTest {

    public static final QuestionRepository DUMMY_QUESTION_REPOSITORY = null;

    @Test
    void askQuestionWithoutIdThenRedirectsToStart() {
        // given
        QuizSessionServiceBuilder quizSessionServiceBuilder = new QuizSessionServiceBuilder();
        QuizSessionService quizSessionService = quizSessionServiceBuilder
                .withSingleChoiceQuestion()
                .withQuiz(42L)
                .withQuizSession()
                .build();
        QuestionRepository questionRepository = quizSessionServiceBuilder.questionRepository();
        QuizController quizController = new QuizController(quizSessionService, questionRepository);

        quizController.start(42L);
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
        QuizSessionServiceBuilder quizSessionServiceBuilder = new QuizSessionServiceBuilder();
        QuizSessionService quizSessionService = quizSessionServiceBuilder
                .withSingleChoiceQuestion()
                .withQuiz(43L)
                .withQuizSession()
                .build();
        QuestionRepository questionRepository = quizSessionServiceBuilder.questionRepository();
        QuizController quizController = new QuizController(quizSessionService, questionRepository);
        quizController.start(43L);
        quizController.askQuestion(new ConcurrentModel(), "stub-id-1");
        AskQuestionForm askQuestionForm = AskQuestionForm.from(quizSessionServiceBuilder.question());

        // when
        askQuestionForm.setSelectedChoices(quizSessionServiceBuilder.correctChoicesForQuestion());
        quizController.questionResponse(askQuestionForm, "stub-id-1");

        // then
        QuizSession session1 = quizSessionService.findSessionByToken("stub-id-1");
        assertThat(session1.responses())
                .hasSize(1);
    }

    @Test
    void answerQuestionForFirstOfTwoSessionsAddsResponseToFirstSession() {
        // given
        QuizSessionServiceBuilder quizSessionServiceBuilder = new QuizSessionServiceBuilder();
        QuizSessionService quizSessionService = quizSessionServiceBuilder
                .withSingleChoiceQuestion()
                .withQuiz(55L)
                .withQuizSession()
                .build();
        QuestionRepository questionRepository = quizSessionServiceBuilder.questionRepository();
        QuizController quizController = new QuizController(quizSessionService, questionRepository);
        quizController.start(55L);
        quizController.start(55L);
        quizController.askQuestion(new ConcurrentModel(), "stub-id-1");
        AskQuestionForm askQuestionForm = AskQuestionForm.from(quizSessionServiceBuilder.question());

        // when
        askQuestionForm.setSelectedChoices(quizSessionServiceBuilder.correctChoicesForQuestion());
        quizController.questionResponse(askQuestionForm, "stub-id-1");

        // then
        QuizSession session1 = quizSessionService.findSessionByToken("stub-id-1");
        assertThat(session1.responses())
                .hasSize(1);
        QuizSession session2 = quizSessionService.findSessionByToken("stub-id-2");
        assertThat(session2.responses())
                .isEmpty();
    }

    @Test
    void askQuestionRedirectsToResultForTheFinishedSession() {
        // given
        QuizSessionService quizSessionService = new QuizSessionService(null, null, null) {
            @Override
            public boolean isFinished(String token) {
                return true;
            }
        };
        QuizController quizController = new QuizController(quizSessionService, null);

        // when
        String redirect = quizController.askQuestion(new ConcurrentModel(), "finished");

        // then
        assertThat(redirect)
                .isEqualTo("redirect:/result?token=finished");
    }
}
