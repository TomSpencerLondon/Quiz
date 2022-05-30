package com.example.quiz.domain;

import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizTest {

    @Test
    void new_quiz_hasNoQuestions() {
        // Given
        Quiz quiz = new Quiz("Quiz 1", Collections.EMPTY_LIST);

        // Then
        List<QuestionId> questionIds = quiz.questionIds();
        assertThat(questionIds)
                .isEmpty();
    }

    @Test
    void new_quiz_hasOneQuestion() {
        // Given
        List<Choice> choices = List.of(new Choice("Answer 1", true), new Choice("Answer 2", false));
        final Question question = new Question(
                "Question 1",
                new SingleChoice(choices)
        );

        List<QuestionId> questionIds = List.of(question).stream().map(Question::getId).toList();
        Quiz quiz = new Quiz("Quiz 1", questionIds);

        List<QuestionId> questionIdsResult = quiz.questionIds();

        // Then
        assertThat(questionIdsResult).isEqualTo(questionIds);
    }

    @Test
    void givenQuizWithQuestionsReturnsFirstQuestionWhenAsked() {
        Question question1 = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        question1.setId(QuestionId.of(54L));
        Question question2 = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        question2.setId(QuestionId.of(66L));
        List<QuestionId> questionIds = List.of(question1, question2).stream().map(Question::getId).toList();
        Quiz quiz = new Quiz("Quiz 1", questionIds);

        QuestionId questionId = quiz.firstQuestion();

        assertThat(questionId)
                .isEqualTo(question1.getId());
    }

    @Test
    void givenQuizHasANextQuestionWhenAskedForNextQuestionQuizReturnsNextQuestion() {
        Question question1 = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        question1.setId(QuestionId.of(54L));
        Question question2 = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        question2.setId(QuestionId.of(66L));

        List<QuestionId> questionIds = List.of(question1, question2).stream().map(Question::getId).toList();
        Quiz quiz = new Quiz("Quiz 1", questionIds);

        QuestionId nextQuestionId = quiz.nextQuestionAfter(question1.getId());

        assertThat(nextQuestionId)
                .isEqualTo(question2.getId());
    }

    @Test
    void givenQuizHasNoNextQuestionWhenAskedForNextQuestionQuizReturnSameQuestion() {
        Question question1 = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        question1.setId(QuestionId.of(54L));
        List<QuestionId> questionIds = List.of(question1).stream().map(Question::getId).toList();
        Quiz quiz = new Quiz("Quiz 1", questionIds);
        QuestionId nextQuestionId = quiz.nextQuestionAfter(question1.getId());

        assertThat(nextQuestionId)
                .isEqualTo(question1.getId());
    }

    @Test
    void givenLastQuestionIdThenIsLastQuestionIdReturnsTrue() {
        Question question1 = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        question1.setId(QuestionId.of(54L));
        Question question2 = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        question2.setId(QuestionId.of(66L));

        List<QuestionId> questionIds = List.of(question1, question2).stream().map(Question::getId).toList();
        Quiz quiz = new Quiz("Quiz 1", questionIds);

        assertThat(quiz.isLastQuestion(question2.getId()))
                .isTrue();
    }

    @Test
    void givenQuizWithTwoQuestionsWhenIsLastWithFirstQuestionIdReturnsFalse() {
        Question question1 = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        question1.setId(QuestionId.of(54L));
        Question question2 = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        question2.setId(QuestionId.of(66L));

        List<QuestionId> questionIds = List.of(question1, question2).stream().map(Question::getId).toList();
        Quiz quiz = new Quiz("Quiz 1", questionIds);

        assertThat(quiz.isLastQuestion(question1.getId()))
                .isFalse();
    }
}
