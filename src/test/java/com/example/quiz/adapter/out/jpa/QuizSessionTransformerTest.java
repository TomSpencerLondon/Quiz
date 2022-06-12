package com.example.quiz.adapter.out.jpa;

import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class QuizSessionTransformerTest {
    InMemoryQuestionRepository questionRepository;
    ResponseTransformer responseTransformer;
    QuizSessionTransformer quizSessionTransformer;

    @BeforeEach
    void setUp() {
        questionRepository = new InMemoryQuestionRepository();
        responseTransformer = new ResponseTransformer(questionRepository);
        quizSessionTransformer = new QuizSessionTransformer(questionRepository, responseTransformer);
    }

    @Test
    void quizSessionDboToQuizSession() {
        // given
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder
                .withQuestionRepository(questionRepository)
                .withDefaultSingleChoice().save();
        Quiz quiz = new QuizBuilder().withQuestions(question).save();
        QuizSession expected = new QuizSessionBuilder()
                .withId(1L)
                .withQuiz(quiz)
                .withQuestion(question).build();

        QuizSessionDbo quizSessionDbo = new QuizSessionDboBuilder()
                .withId(1L)
                .withToken("stub-token-1")
                .withQuizId(quiz.getId())
                .withCurrentQuestionId(question.getId())
                .build();

        // when
        QuizSession quizSession = quizSessionTransformer.toQuizSession(quizSessionDbo);

        // then

        assertThat(quizSession)
                .usingRecursiveComparison()
                .ignoringFields("question")
                .isEqualTo(expected);
    }

    @Test
    void quizSessionToQuizSessionDbo() {
        // given
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder
                .withQuestionId(0L)
                .withDefaultSingleChoice()
                .save();

        Quiz quiz = new QuizBuilder().withQuestions(question).save();
        QuizSession quizSession = new QuizSessionBuilder()
                .withId(1L)
                .withQuestion(question)
                .withQuiz(quiz)
                .build();

        quizSession.respondWith(question, quiz, questionBuilder.correctChoiceForQuestion().id());

        Response response = new ResponseBuilder()
                .withQuestion(question)
                .withCorrectChoice().build();

        QuizSessionDbo expected = new QuizSessionDboBuilder()
                .withId(1L)
                .withQuizId(quiz.getId())
                .withToken("stub-token-1")
                .withCurrentQuestionId(question.getId())
                .withResponse(response)
                .build();

        // when
        QuizSessionDbo quizSessionDbo = quizSessionTransformer.toQuizSessionDbo(quizSession);

        // then
        assertThat(quizSessionDbo)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private QuizSessionDbo createQuizSessionDbo(Choice choice, Question question) {
        QuizSessionDbo expected = new QuizSessionDbo();
        expected.setId(0L);
        expected.setToken("stub-token-1");
        ResponseDbo responseDbo = new ResponseDbo();
        responseDbo.setQuestionId(question.getId().id());
        responseDbo.setChoiceIds(Set.of(choice.getId().id()));
        expected.setResponses(List.of(responseDbo));
        expected.setStartedAt(ZonedDateTime.of(2022, 3, 10, 5, 10, 0, 0, ZoneOffset.UTC));
        expected.setCurrentQuestionId(0L);
        return expected;
    }

    private QuizSession createQuizSession(Choice choice1, Question question) {
        List<Response> responses = List.of(new Response(question.getId(), question.isCorrectAnswer(choice1), choice1));
        ZonedDateTime startedAt = ZonedDateTime.of(2022, 3, 10, 5, 10, 0, 0, ZoneOffset.UTC);
        return new QuizSession(QuizSessionId.of(0L), "stub-token-1", question.getId(), responses, startedAt);
    }
}