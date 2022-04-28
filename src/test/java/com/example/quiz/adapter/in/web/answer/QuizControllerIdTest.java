package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.application.QuizService;
import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.QuizSessionServiceTestFactory;
import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.InMemoryQuizSessionRepository;
import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.application.port.TokenGenerator;
import com.example.quiz.domain.FinishedQuizSession;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.QuizSession;
import com.example.quiz.domain.UnfinishedQuizSession;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

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
        StubQuestionRepository stubQuestionRepository = new StubQuestionRepository();
        Question singleChoiceQuestion = stubQuestionRepository.findAll().get(0);
        QuizService quizService = new QuizService(stubQuestionRepository);
        QuizSessionService quizSessionService = new QuizSessionService(quizService, new InMemoryQuizSessionRepository());
        QuizController quizController = new QuizController(quizSessionService, new StubTokenGenerator(), stubQuestionRepository);
        quizController.start();
        quizController.askQuestion(new ConcurrentModel(), "stub-id-1");

        QuizSession sessionByToken = quizSessionService.findSessionByToken("stub-id-1");
        AskQuestionForm askQuestionForm = AskQuestionForm.from(singleChoiceQuestion);

        // when
        long selectedChoiceId = singleChoiceQuestion.choices().get(1).getId().id();
        askQuestionForm.setSelectedChoices(selectedChoiceId);
        quizController.questionResponse(askQuestionForm, "stub-id-1");

        // then
        QuizSession session1 = quizSessionService.findSessionByToken("stub-id-1");
        assertThat(session1.responses())
                .hasSize(1);
    }

    @Test
    void answerQuestionForFirstOfTwoSessionsAddsResponseToFirstSession() {
        // given
        StubQuestionRepository stubQuestionRepository = new StubQuestionRepository();
        Question singleChoiceQuestion = stubQuestionRepository.findAll().get(0);
        QuizService quizService = new QuizService(stubQuestionRepository);
        QuizSessionService quizSessionService = new QuizSessionService(quizService, new InMemoryQuizSessionRepository());
        QuizController quizController = new QuizController(quizSessionService, new StubTokenGenerator(), stubQuestionRepository);
        quizController.start();
        quizController.start();
        quizController.askQuestion(new ConcurrentModel(), "stub-id-1");
        AskQuestionForm askQuestionForm = AskQuestionForm.from(singleChoiceQuestion);

        // when
        long selectedChoiceId = singleChoiceQuestion.choices().get(1).getId().id();
        askQuestionForm.setSelectedChoices(selectedChoiceId);
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
