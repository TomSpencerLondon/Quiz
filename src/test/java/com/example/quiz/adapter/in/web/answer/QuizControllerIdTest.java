package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.port.*;
import com.example.quiz.domain.*;
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
        Question question = new QuestionBuilder().withDefaultSingleChoice().save();
        QuizRepository quizRepository = new InMemoryQuizRepository();
        Quiz quiz = new QuizBuilder()
                .withQuestions(question)
                .build();
        quizRepository.save(quiz);
        QuizSessionService quizSessionService = new QuizSessionService(new InMemoryQuizSessionRepository(), quizRepository, new StubTokenGenerator());
        QuizController quizController = new QuizController(quizSessionService, DUMMY_QUESTION_REPOSITORY);
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
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder.withDefaultSingleChoice().save();
        QuestionRepository questionRepository = questionBuilder.questionRepository();
        QuizRepository quizRepository = new InMemoryQuizRepository();
        List<QuestionId> questionIds = List.of(question)
                                           .stream().map(Question::getId).toList();
        Quiz quiz = new Quiz("Quiz 1", questionIds);
        quizRepository.save(quiz);
        QuizSessionService quizSessionService = new QuizSessionService(new InMemoryQuizSessionRepository(), quizRepository, new StubTokenGenerator());
        QuizController quizController = new QuizController(quizSessionService, questionRepository);
        quizController.start(0L);
        quizController.askQuestion(new ConcurrentModel(), "stub-id-1");
        AskQuestionForm askQuestionForm = AskQuestionForm.from(question);

        // when
        long selectedChoiceId = question.choices().get(1).getId().id();
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

        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder.withDefaultSingleChoice().withQuestionId(1L).save();
        QuestionRepository questionRepository = questionBuilder.questionRepository();
        QuizBuilder quizBuilder = new QuizBuilder();
        quizBuilder.withQuestions(question).save();
        QuizRepository quizRepository = quizBuilder.quizRepository();
        QuizSessionService quizSessionService = new QuizSessionService(new InMemoryQuizSessionRepository(), quizRepository, new StubTokenGenerator());
        QuizController quizController = new QuizController(quizSessionService, questionRepository);
        quizController.start(1L);
        quizController.start(1L);
        quizController.askQuestion(new ConcurrentModel(), "stub-id-1");
        AskQuestionForm askQuestionForm = AskQuestionForm.from(question);

        // when
        long selectedChoiceId = question.choices().get(1).getId().id();
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
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder.withQuestionId(5L).withDefaultSingleChoice().save();
        Question question2 = questionBuilder.withQuestionId(6L).withDefaultSingleChoice().save();
        QuestionRepository questionRepository = questionBuilder.questionRepository();
        QuizBuilder quizBuilder = new QuizBuilder();
        Quiz quiz1 = quizBuilder.withQuestions(question, question2).save();
        Quiz quiz2 = quizBuilder.withQuestions(question).save();
        QuizRepository quizRepository = quizBuilder.quizRepository();
        QuizSessionBuilder quizSessionBuilder = new QuizSessionBuilder();
        quizSessionBuilder.withQuiz(quiz1).withQuestion(question).withToken("unfinished").save();
        quizSessionBuilder.withQuiz(quiz2).withQuestion(question).withToken("finished").save();
        QuizSessionRepository quizSessionRepository = quizSessionBuilder.quizSessionRepository();
        QuizSessionService quizSessionService = new QuizSessionService(quizSessionRepository, quizRepository, null);
        QuizController quizController = new QuizController(quizSessionService, questionRepository);
        quizController.askQuestion(new ConcurrentModel(), "finished");
        AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setSelectedChoices(questionBuilder.correctChoiceForQuestion().id());
        askQuestionForm.setQuestion(question.text());
        quizController.questionResponse(askQuestionForm, "finished");

        // when
        String redirect = quizController.askQuestion(new ConcurrentModel(), "finished");

        // then
        assertThat(redirect)
                .isEqualTo("redirect:/result?token=finished");
    }


}
