package com.example.quiz.domain;

import com.example.quiz.domain.factories.QuizTestFactory;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuizSessionTest {
    @Test
    void emptyQuizThrowsException() {
        // Given
        Quiz quiz = QuizTestFactory.createQuizWithSingleChoiceQuestions(0);

        // Then
        assertThatThrownBy(quiz::start)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void sessionStartsWithTheFirstQuestion() {
        // Given
        final ChoiceType choice = new SingleChoice(Collections.singletonList(new Choice("Answer 1", true)));

        final Question question = new Question("Question 1", choice);
        QuestionId questionId = QuestionId.of(45L);
        question.setId(questionId);
        List<Question> questions = List.of(question);
        Quiz quiz = new Quiz(questions);

        // When
        QuizSession session = quiz.start();

        // Then
        assertThat(session.currentQuestionId())
                .isEqualTo(questionId);
    }

    @Test
//  testTakerCanCheckIfSessionWithOneQuestionIsFinished
    void givenQuizWithOneQuestionWhenQuestionIsAnsweredSessionIsFinished() {
        // Given
        Quiz quiz = QuizTestFactory.createQuizWithSingleChoiceQuestions(1);
        QuizSession session = quiz.start();

        // when
        session.respondWith(new Choice("Answer 1"));

        // Then
        assertThat(session.isFinished())
                .isTrue();
    }

    @Test
    void quizWithThreeQuestionsWhenAnsweringTwoQuestionsSessionIsNotFinished() {
        // Given
        Quiz quiz = QuizTestFactory.createQuizWithSingleChoiceQuestions(3);
        QuizSession session = quiz.start();

        // when
        session.respondWith(new Choice("Answer 1"));
        session.respondWith(new Choice("Answer 2"));

        // Then
        assertThat(session.isFinished())
                .isFalse();
    }

    @Test
    void testTakerCanCheckIfSessionWithThreeQuestionsIsFinishedAfterThirdQuestion() {
        Quiz quiz = QuizTestFactory.createQuizWithSingleChoiceQuestions(3);
        QuizSession session = quiz.start();

        // when
        session.respondWith(new Choice("Answer 1"));
        session.respondWith(new Choice("Answer 2"));
        session.respondWith(new Choice("Answer 2"));

        // Then
        assertThat(session.isFinished())
                .isTrue();
    }

    @Test
    void grade_gives_number_of_correct_responses_for_Session() {
        // Given
        Quiz quiz = QuizTestFactory.createQuizWithSingleChoiceQuestions(3);
        QuizSession session = quiz.start();

        // When
        session.respondWith(new Choice("Answer 1", true));
        session.respondWith(new Choice("Answer 2", false));
        session.respondWith(new Choice("Answer 2", false));

        // Then
        assertThat(session.correctResponsesCount())
                .isEqualTo(1L);
    }

    @Test
    void counts_incorrect_responses() {
        // Given
        Quiz quiz = QuizTestFactory.createQuizWithSingleChoiceQuestions(3);
        QuizSession session = quiz.start();

        // When
        session.respondWith(new Choice("Answer 1", true));
        session.respondWith(new Choice("Answer 2", false));
        session.respondWith(new Choice("Answer 2", false));

        // Then
        assertThat(session.incorrectResponsesCount())
                .isEqualTo(2L);
    }

    @Test
    void calculatesGradeForFinishedTest() {
        // Given
        Quiz quiz = QuizTestFactory.createQuizWithSingleChoiceQuestions(3);
        QuizSession session = quiz.start();
        QuestionId questionId1 = session.currentQuestionId();
        Choice choice1 = new Choice("Answer 1", true);
        QuestionId questionId2 = session.currentQuestionId();
        Choice choice2 = new Choice("Answer 2", false);
        QuestionId questionId3 = session.currentQuestionId();
        Choice choice3 = new Choice("Answer 2", false);
        session.respondWith(choice1);
        session.respondWith(choice2);
        session.respondWith(choice3);
        List<Response> responses = List.of(
                new Response(questionId1, true, choice1),
                new Response(questionId2, false, choice2),
                new Response(questionId3, false, choice3));

        // When
        final Grade grade = new Grade(responses, 1, 2);

        // Then
        final Grade result = session.grade();
        assertThat(result)
                .isEqualTo(grade);
    }

    @Test
    void returnSameQuestionIfItHasntBeenAnswered() {
        // Given
        Quiz quiz = QuizTestFactory.createQuizWithSingleChoiceQuestions(1);
        QuizSession session = quiz.start();

        // When
        QuestionId questionId1 = session.currentQuestionId();
        QuestionId questionId2 = session.currentQuestionId();

        // Then
        assertThat(questionId1)
                .isEqualTo(questionId2);
    }

    @Test
    void quizWithTwoQuestionsWhenResponseToFirstQuestionThenSecondQuestionIsCurrent() {
        // Given
        final Quiz quiz = QuizTestFactory.createQuizWithSingleChoiceQuestions(2);
        final QuizSession session = quiz.start();
        final QuestionId questionId1 = session.currentQuestionId();
        session.respondWith(new Choice("text"));

        // When
        final QuestionId questionId2 = session.currentQuestionId();

        // Then
        assertThat(questionId1)
                .isNotEqualTo(questionId2);
    }

    @Test
    void respondWithChoiceThenResponseHasSelectedChoice() {
        // Given
        List<Choice> choices = List.of(
                new Choice(ChoiceId.of(44L), "Answer 1", true),
                new Choice(ChoiceId.of(74L), "Answer 2", false),
                new Choice(ChoiceId.of(37L), "Answer 3", false),
                new Choice(ChoiceId.of(55L), "Answer 4", false)
        );
        ChoiceType singleChoice = new SingleChoice(choices);
        Question question = new Question("Question 1", singleChoice);
        question.setId(QuestionId.of(83L));
        Quiz quiz = new Quiz(Collections.singletonList(question));
        QuizSession session = quiz.start();

        // When
        Choice choice = choices.get(1);
        session.respondWith(choice.getId().id());

        // Then
        assertThat(session.responses().get(0).choices())
                .containsExactly(choice);

    }
}
