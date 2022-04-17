package com.example.quiz.adapter.out.jpa;

import com.example.quiz.application.port.InMemoryChoiceRepository;
import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.domain.*;
import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class QuizSessionTransformerTest {
    InMemoryChoiceRepository choiceRepository = new InMemoryChoiceRepository();
    InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
    final QuizSessionTransformer quizSessionTransformer = new QuizSessionTransformer(questionRepository, choiceRepository);

    @Test
    void quizSessionDboToQuizSession() {
        // given
        SingleChoice singleChoice = new SingleChoice(List.of(
                new Choice(ChoiceId.of(1L), "C1", true),
                new Choice(ChoiceId.of(2L), "C2", false)
        ));
        Question question = new Question("Q1", singleChoice);
        Choice choice1 = new Choice(ChoiceId.of(1L), "C1", true);
        Choice choice2 = new Choice(ChoiceId.of(2L), "C1", false);
        questionRepository.save(question);
        choiceRepository.save(choice1);
        choiceRepository.save(choice2);

        QuizSession expected = new QuizSession();
        expected.setId(QuizSessionId.of(1L));
        expected.setToken("stub-token-1");
        expected.setQuestion(question);
        expected.setResponse(new Response(question.getId(), question.isCorrectAnswer(choice1), choice1));
        expected.setStartedAt(ZonedDateTime.of(2022, 3, 10, 5, 10, 0, 0, ZoneOffset.UTC));

        QuizSessionDbo quizSessionDbo = new QuizSessionDbo();
        quizSessionDbo.setId(1L);
        quizSessionDbo.setToken("stub-token-1");
        ResponseDbo responseDbo = new ResponseDbo();
        responseDbo.setId(1L);
        responseDbo.setQuestionId(question.getId().id());
        responseDbo.setChoiceIds(Set.of(choice1.getId().id()));
        quizSessionDbo.setResponses(List.of(responseDbo));
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