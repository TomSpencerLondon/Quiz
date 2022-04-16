package com.example.quiz.adapter.out.jpa;

import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.domain.*;
import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class QuizSessionTransformerTest {
    InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
    final QuizSessionTransformer quizSessionTransformer = new QuizSessionTransformer(questionRepository);

    @Test
    void quizSessionDboToQuizSession() {
        // given
        SingleChoice singleChoice = new SingleChoice(List.of(
                new Choice(ChoiceId.of(1L), "C1", true),
                new Choice(ChoiceId.of(2L), "C2", false)
        ));
        questionRepository.save(new Question("Q1", singleChoice));

        QuizSession expected = new QuizSession();
        expected.setId(QuizSessionId.of(1L));
        expected.setToken("stub-token-1");
        Question question = new Question("Q1", singleChoice);
        expected.setQuestion(question);
        expected.setResponse(new Response(question, new Choice(ChoiceId.of(1L), "C1", true)));
        expected.setStartedAt(ZonedDateTime.of(2022, 3, 10, 5, 10, 0, 0, ZoneOffset.UTC));

        QuizSessionDbo quizSessionDbo = new QuizSessionDbo();
        quizSessionDbo.setId(1L);
        quizSessionDbo.setToken("stub-token-1");
        ResponseDbo responseDbo1 = new ResponseDbo();
        responseDbo1.setId(1L);
        responseDbo1.setChoiceIds(Set.of(1L, 2L));
        ResponseDbo responseDbo2 = new ResponseDbo();
        responseDbo2.setId(2L);
        responseDbo2.setChoiceIds(Set.of(3L, 4L));
        quizSessionDbo.setResponses(List.of(responseDbo1, responseDbo2));
        quizSessionDbo.setStartedAt(ZonedDateTime.of(2022, 3, 10, 5, 10, 0, 0, ZoneOffset.UTC));
        quizSessionDbo.setCurrentQuestionId(1L);

        // when
        QuizSession quizSession = quizSessionTransformer.toQuizSession(quizSessionDbo);

        // then

        assertThat(quizSession)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}