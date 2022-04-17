package com.example.quiz.adapter.out.jpa;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QuizSessionJpaRepositoryTest implements TestContainerConfiguration {

    @Autowired
    QuizSessionJpaRepository quizSessionJpaRepository;


    @Test
    void savesAndRetrievesAQuizSessionWithNoResponses() {
        // Given
        QuizSessionDbo quizSessionDbo = new QuizSessionDbo();
        quizSessionDbo.setCurrentQuestionId(2L);
        quizSessionDbo.setStartedAt(ZonedDateTime.of(2022, 3, 10, 5, 10, 0, 0, ZoneOffset.UTC));
        quizSessionDbo.setToken("stub-1");
        quizSessionDbo.setResponses(Collections.emptyList());

        // When
        QuizSessionDbo savedSession = quizSessionJpaRepository.save(quizSessionDbo);

        // Then
        assertThat(savedSession.getId())
                .isNotNull();
    }

    @Test
    void saveAndRetrieveAQuizSessionWithMultipleResponses() {
        // Given
        QuizSessionDbo quizSessionDbo = new QuizSessionDbo();
        quizSessionDbo.setCurrentQuestionId(2L);
        quizSessionDbo.setStartedAt(ZonedDateTime.of(2022, 3, 10, 5, 10, 0, 0, ZoneOffset.UTC));
        quizSessionDbo.setToken("stub-1");
        ResponseDbo responseDbo1 = new ResponseDbo();
        responseDbo1.setChoiceIds(Set.of(1L, 2L));
        ResponseDbo responseDbo2 = new ResponseDbo();
        responseDbo2.setChoiceIds(Set.of(3L, 4L));
        quizSessionDbo.setResponses(List.of(responseDbo1, responseDbo2));

        // When
        QuizSessionDbo savedSession = quizSessionJpaRepository.save(quizSessionDbo);

        // Then
        assertThat(savedSession.getResponses().size())
                .isEqualTo(2);
    }
}