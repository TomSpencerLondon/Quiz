package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.application.QuizService;
import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.port.*;
import com.example.quiz.domain.*;
import com.example.quiz.domain.factories.QuizTestFactory;
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
        Question singleChoiceQuestion = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        inMemoryQuestionRepository.save(singleChoiceQuestion);
        QuizService quizService = new QuizService(inMemoryQuestionRepository);
        QuizRepository quizRepository = new InMemoryQuizRepository();
        quizRepository.save(QuizTestFactory.createQuizWithSingleChoiceQuestion());
        QuizSessionService quizSessionService1 = new QuizSessionService(quizService, new InMemoryQuizSessionRepository(), quizRepository, new StubTokenGenerator());
        QuizSessionService quizSessionService = quizSessionService1;
        QuizController quizController = new QuizController(quizSessionService, new StubTokenGenerator(), DUMMY_QUESTION_REPOSITORY);
        quizController.start(0L);
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
        QuizRepository quizRepository = new InMemoryQuizRepository();
        List<Question> questions = List.of(singleChoiceQuestion);
        Quiz quiz = new Quiz(questions);
        quizRepository.save(quiz);
        QuizSessionService quizSessionService = new QuizSessionService(quizService, new InMemoryQuizSessionRepository(), quizRepository, new StubTokenGenerator());
        QuizController quizController = new QuizController(quizSessionService, new StubTokenGenerator(), stubQuestionRepository);
        quizController.start(0L);
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
    }

    @Test
    void answerQuestionForFirstOfTwoSessionsAddsResponseToFirstSession() {
        // given
        StubQuestionRepository stubQuestionRepository = new StubQuestionRepository();
        Question singleChoiceQuestion = stubQuestionRepository.findAll().get(0);
        QuizService quizService = new QuizService(stubQuestionRepository);
        QuizRepository quizRepository = new InMemoryQuizRepository();
        quizRepository.save(QuizTestFactory.createQuizWithSingleChoiceQuestion());
        QuizSessionService quizSessionService = new QuizSessionService(quizService, new InMemoryQuizSessionRepository(), quizRepository, new StubTokenGenerator());
        QuizController quizController = new QuizController(quizSessionService, new StubTokenGenerator(), stubQuestionRepository);
        quizController.start(0L);
        quizController.start(0L);
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
        QuizSessionService quizSessionService = new QuizSessionService(quizService, quizSessionRepository, null, null);

        QuizController quizController = new QuizController(quizSessionService, DUMMY_TOKEN_GENERATOR, DUMMY_QUESTION_REPOSITORY);

        // when
        String redirect = quizController.askQuestion(new ConcurrentModel(), "finished");

        // then
        assertThat(redirect)
                .isEqualTo("redirect:/result?token=finished");
    }



}
