package com.example.quiz.adapter.out.jpa;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QuizSessionJpaRepositoryTest implements TestContainerConfiguration {

    @Autowired
    QuizSessionJpaRepository quizSessionJpaRepository;


    @Test
    void savesAndRetrievesAQuizSessionWithNoResponses() {
        QuizSessionDbo quizSessionDbo = new QuizSessionDbo();
        quizSessionDbo.setCurrentQuestionId(2L);
        quizSessionDbo.setStartedAt(ZonedDateTime.of(2022, 3, 10, 5, 10, 0, 0, ZoneOffset.UTC));
        quizSessionDbo.setToken("stub-1");
        quizSessionDbo.setResponses(Collections.emptyList());

        QuizSessionDbo savedSession = quizSessionJpaRepository.save(quizSessionDbo);

        assertThat(savedSession.getId())
                .isNotNull();
    }
}