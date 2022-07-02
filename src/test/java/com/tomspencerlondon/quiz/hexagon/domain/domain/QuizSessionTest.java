package com.tomspencerlondon.quiz.hexagon.domain.domain;

import com.tomspencerlondon.quiz.hexagon.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizSessionTest {

    @Test
    void sessionStartsWithTheFirstQuestion() {
        // Given
        Quiz quiz = new Quiz("Quiz 1", List.of(QuestionId.of(1L)));
        quiz.setId(QuizId.of(65L));

        // When
        QuizSession session = new QuizSession(QuestionId.of(1L), "stub-1", quiz.getId());

        // Then
        assertThat(session.currentQuestionId())
                .isEqualTo(QuestionId.of(1L));
    }

    @Test
//  testTakerCanCheckIfSessionWithOneQuestionIsFinished
    void givenQuizWithOneQuestionWhenQuestionIsAnsweredSessionIsFinished() {
        // Given
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder
                .withQuestionId(7L)
                .withDefaultSingleChoice()
                .build();

        Quiz quiz = new Quiz("Quiz 1", List.of(QuestionId.of(1L)));
        quiz.setId(QuizId.of(65L));
        QuizSession session = new QuizSessionBuilder()
                .withQuestion(question)
                .withQuiz(quiz)
                .build();

        // when
        session.respondWith(question, quiz, question.choices().get(0).getId().id());

        // Then
        assertThat(session.isFinished(quiz))
                .isTrue();
    }

    @Test
    void quizWithThreeQuestionsWhenAnsweringTwoQuestionsSessionIsNotFinished() {
        // Given
        QuestionBuilder questionBuilder1 = new QuestionBuilder();
        Question question1 = questionBuilder1
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .build();
        QuestionBuilder questionBuilder2 = new QuestionBuilder();
        Question question2 = questionBuilder2
                .withQuestionId(2L)
                .withDefaultSingleChoice()
                .build();
        Question question3 = new QuestionBuilder()
                .withQuestionId(3L)
                .withDefaultSingleChoice()
                .build();

        Quiz quiz = new Quiz("Quiz 1", List.of(question1.getId(), question2.getId(), question3.getId()));
        QuizSession session = new QuizSessionBuilder()
                .withQuestion(question1)
                .withQuiz(quiz)
                .build();

        // when
        session.respondWith(question1, quiz, question1.choices().get(0).getId().id());
        session.respondWith(question2, quiz, question2.choices().get(0).getId().id());

        // Then
        assertThat(session.isFinished(quiz))
                .isFalse();
    }

    @Test
    void testTakerCanCheckIfSessionWithThreeQuestionsIsFinishedAfterThirdQuestion() {
        // Given
        QuestionBuilder questionBuilder1 = new QuestionBuilder();
        Question question1 = questionBuilder1
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .build();
        QuestionBuilder questionBuilder2 = new QuestionBuilder();
        Question question2 = questionBuilder2
                .withQuestionId(2L)
                .withDefaultSingleChoice()
                .build();
        QuestionBuilder questionBuilder3 = new QuestionBuilder();
        Question question3 = questionBuilder3
                .withQuestionId(3L)
                .withDefaultSingleChoice()
                .build();

        Quiz quiz = new Quiz("Quiz 1", List.of(question1.getId(), question2.getId(), question3.getId()));
        QuizSession session = new QuizSessionBuilder()
                .withQuestion(question1)
                .withQuiz(quiz)
                .build();

        // when
        session.respondWith(question1, quiz, question1.choices().get(0).getId().id());
        session.respondWith(question2, quiz, question2.choices().get(0).getId().id());
        session.respondWith(question3, quiz, question3.choices().get(0).getId().id());

        // Then
        assertThat(session.isFinished(quiz))
                .isTrue();
    }

    @Test
    void grade_gives_number_of_correct_responses_for_Session() {
        // Given
        QuestionBuilder questionBuilder1 = new QuestionBuilder();
        Question question1 = questionBuilder1
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .build();
        QuestionBuilder questionBuilder2 = new QuestionBuilder();
        Question question2 = questionBuilder2
                .withQuestionId(2L)
                .withDefaultSingleChoice()
                .build();
        QuestionBuilder questionBuilder3 = new QuestionBuilder();
        Question question3 = questionBuilder3
                .withQuestionId(3L)
                .withDefaultSingleChoice()
                .build();

        Quiz quiz = new Quiz("Quiz 1", List.of(question1.getId(), question2.getId(), question3.getId()));
        QuizSession session = new QuizSessionBuilder()
                .withQuestion(question1)
                .withQuiz(quiz)
                .build();

        // when
        session.respondWith(question1, quiz, question1.choices().get(0).getId().id());
        session.respondWith(question2, quiz, question2.choices().get(1).getId().id());
        session.respondWith(question3, quiz, question3.choices().get(2).getId().id());

        // Then
        assertThat(session.correctResponsesCount())
                .isEqualTo(1L);
    }

    @Test
    void counts_incorrect_responses() {
        // Given
        QuestionBuilder questionBuilder1 = new QuestionBuilder();
        Question question1 = questionBuilder1
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .build();
        QuestionBuilder questionBuilder2 = new QuestionBuilder();
        Question question2 = questionBuilder2
                .withQuestionId(2L)
                .withDefaultSingleChoice()
                .build();
        QuestionBuilder questionBuilder3 = new QuestionBuilder();
        Question question3 = questionBuilder3
                .withQuestionId(3L)
                .withDefaultSingleChoice()
                .build();

        Quiz quiz = new Quiz("Quiz 1", List.of(question1.getId(), question2.getId(), question3.getId()));
        QuizSession session = new QuizSessionBuilder()
                .withQuestion(question1)
                .withQuiz(quiz)
                .build();

        // when
        session.respondWith(question1, quiz, question1.choices().get(0).getId().id());
        session.respondWith(question2, quiz, question2.choices().get(1).getId().id());
        session.respondWith(question3, quiz, question3.choices().get(2).getId().id());

        // Then
        assertThat(session.incorrectResponsesCount())
                .isEqualTo(2L);
    }

    @Test
    void calculatesGradeForFinishedTest() {
        // Given
        QuestionBuilder questionBuilder1 = new QuestionBuilder();
        Question question1 = questionBuilder1
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .build();
        QuestionBuilder questionBuilder2 = new QuestionBuilder();
        Question question2 = questionBuilder2
                .withQuestionId(2L)
                .withDefaultSingleChoice()
                .build();
        QuestionBuilder questionBuilder3 = new QuestionBuilder();
        Question question3 = questionBuilder3
                .withQuestionId(3L)
                .withDefaultSingleChoice()
                .build();

        Quiz quiz = new Quiz("Quiz 1", List.of(question1.getId(), question2.getId(), question3.getId()));
        QuizSession session = new QuizSessionBuilder()
                .withQuestion(question1).withQuiz(quiz).build();

        // When
        session.respondWith(question1, quiz, question1.choices().get(0).getId().id());
        session.respondWith(question2, quiz, question2.choices().get(1).getId().id());
        session.respondWith(question3, quiz, question3.choices().get(2).getId().id());

        // Then
        Grade expectedGrade = new GradeBuilder()
                .withCorrectResponseFor(question1)
                .withIncorrectResponseFor(question2)
                .withIncorrectResponseFor(question3)
                .build();
        final Grade result = session.grade();
        assertThat(result)
                .isEqualTo(expectedGrade);
    }

    @Test
    void returnSameQuestionIfItHasntBeenAnswered() {
        // Given
        Question question1 = new QuestionBuilder()
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .build();
        Question question2 = new QuestionBuilder()
                .withQuestionId(2L)
                .withDefaultSingleChoice()
                .build();
        Question question3 = new QuestionBuilder()
                .withQuestionId(3L)
                .withDefaultSingleChoice()
                .build();

        Quiz quiz = new Quiz("Quiz 1", List.of(question1.getId(), question2.getId(), question3.getId()));
        QuizSession session = new QuizSessionBuilder().withQuestion(question1).withQuiz(quiz).build();

        // When
        final QuestionId questionId1 = session.currentQuestionId();
        QuestionId questionId2 = session.currentQuestionId();

        // Then
        assertThat(questionId1)
                .isEqualTo(questionId2);
    }

    @Test
    void quizWithTwoQuestionsWhenResponseToFirstQuestionThenSecondQuestionIsCurrent() {
        // Given
        QuestionBuilder questionBuilder1 = new QuestionBuilder();
        Question question1 = questionBuilder1
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .build();
        Question question2 = new QuestionBuilder()
                .withQuestionId(2L)
                .withDefaultSingleChoice()
                .build();
        Question question3 = new QuestionBuilder()
                .withQuestionId(3L)
                .withDefaultSingleChoice()
                .build();

        Quiz quiz = new Quiz("Quiz 1", List.of(question1.getId(), question2.getId(), question3.getId()));
        QuizSession session = new QuizSessionBuilder().withQuestion(question1).withQuiz(quiz).build();
        final QuestionId questionId1 = session.currentQuestionId();
        session.respondWith(question1, quiz, question1.choices().get(0).getId().id());

        // When
        final QuestionId questionId2 = session.currentQuestionId();

        // Then
        assertThat(questionId1)
                .isNotEqualTo(questionId2);
    }

    @Test
    void respondWithChoiceThenResponseHasSelectedChoice() {
        // Given
        QuestionBuilder questionBuilder1 = new QuestionBuilder();
        Question question = questionBuilder1
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .build();

        Quiz quiz = new Quiz("Quiz 1", List.of(question.getId()));
        QuizSession session = new QuizSessionBuilder().withQuestion(question).withQuiz(quiz).build();
        Choice choice = question.choices().get(0);

        // When
        session.respondWith(question, quiz, question.choices().get(0).getId().id());

        // Then
        assertThat(session.responses().get(0).choices())
                .containsExactly(choice);
    }
}
