package com.example.quiz.adapter.out.jpa;

import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.domain.*;
import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;
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
        Choice choice1 = new Choice(ChoiceId.of(1L), "C1", true);
        Choice choice2 = new Choice(ChoiceId.of(2L), "C2", false);
        SingleChoice singleChoice = new SingleChoice(List.of(
                choice1,
                choice2
        ));
        Question question = new Question("Q1", singleChoice);
        questionRepository.save(question);
        QuizSession expected = createQuizSession(choice1, question);
        QuizSessionDbo quizSessionDbo = createQuizSessionDbo(choice1, question);

        // when
        QuizSession quizSession = quizSessionTransformer.toQuizSession(quizSessionDbo);

        // then

        assertThat(quizSession)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void quizSessionToQuizSessionDbo() {
        // given
        Question question = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        question.setId(QuestionId.of(0L));
        Choice choice = question.choices().get(0);
        QuizSession quizSession = createQuizSession(choice, question);
        QuizSessionDbo expected = createQuizSessionDbo(choice, question);

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
        QuizSession expected = new QuizSession();
        expected.setId(QuizSessionId.of(0L));
        expected.setToken("stub-token-1");
        expected.setQuestion(question);
        expected.addResponse(new Response(question.getId(), question.isCorrectAnswer(choice1), choice1));
        expected.setStartedAt(ZonedDateTime.of(2022, 3, 10, 5, 10, 0, 0, ZoneOffset.UTC));
        return expected;
    }
}