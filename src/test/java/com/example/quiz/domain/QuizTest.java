package com.example.quiz.domain;

import com.example.quiz.hexagon.domain.Question;
import com.example.quiz.hexagon.domain.QuestionId;
import com.example.quiz.hexagon.domain.Quiz;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizTest {

    @Test
    void new_quiz_hasNoQuestions() {
        // Given
        Quiz quiz = new QuizBuilder().build();

        // Then
        List<QuestionId> questionIds = quiz.questionIds();
        assertThat(questionIds)
                .isEmpty();
    }

    @Test
    void new_quiz_hasOneQuestion() {
        // Given
        Question question = new QuestionBuilder()
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .build();

        List<QuestionId> questionIds = Stream.of(question)
                                             .map(Question::getId)
                                             .toList();
        Quiz quiz = new Quiz("Quiz 1", questionIds);

        List<QuestionId> questionIdsResult = quiz.questionIds();

        // Then
        assertThat(questionIdsResult).isEqualTo(questionIds);
    }

    @Test
    void givenQuizWithQuestionsReturnsFirstQuestionWhenAsked() {
        Question question1 = new QuestionBuilder()
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .build();
        Question question2 = new QuestionBuilder()
                .withQuestionId(66L)
                .withDefaultSingleChoice()
                .build();
        Quiz quiz = new Quiz("Quiz 1", Stream.of(question1, question2).map(Question::getId).toList());

        QuestionId questionId = quiz.firstQuestion();

        assertThat(questionId)
                .isEqualTo(question1.getId());
    }

    @Test
    void givenQuizHasANextQuestionWhenAskedForNextQuestionQuizReturnsNextQuestion() {
        Question question1 = new QuestionBuilder()
                .withQuestionId(54L)
                .withDefaultSingleChoice()
                .build();
        Question question2 = new QuestionBuilder()
                .withQuestionId(66L)
                .withDefaultSingleChoice()
                .build();

        Quiz quiz = new Quiz("Quiz 1", Stream.of(question1, question2).map(Question::getId).toList());

        QuestionId nextQuestionId = quiz.nextQuestionAfter(question1.getId());

        assertThat(nextQuestionId)
                .isEqualTo(question2.getId());
    }

    @Test
    void givenQuizHasNoNextQuestionWhenAskedForNextQuestionQuizReturnSameQuestion() {
        Question question1 = new QuestionBuilder()
                .withQuestionId(54L)
                .withDefaultSingleChoice()
                .build();
        Quiz quiz = new Quiz("Quiz 1", Stream.of(question1).map(Question::getId).toList());
        QuestionId nextQuestionId = quiz.nextQuestionAfter(question1.getId());

        assertThat(nextQuestionId)
                .isEqualTo(question1.getId());
    }

    @Test
    void givenLastQuestionIdThenIsLastQuestionIdReturnsTrue() {
        Question question1 = new QuestionBuilder()
                .withQuestionId(54L)
                .withDefaultSingleChoice()
                .build();
        Question question2 = new QuestionBuilder()
                .withQuestionId(66L)
                .withDefaultSingleChoice()
                .build();

        Quiz quiz = new Quiz("Quiz 1", Stream.of(question1, question2).map(Question::getId).toList());

        assertThat(quiz.isLastQuestion(question2.getId()))
                .isTrue();
    }

    @Test
    void givenQuizWithTwoQuestionsWhenIsLastWithFirstQuestionIdReturnsFalse() {
        Question question1 = new QuestionBuilder()
                .withQuestionId(54L)
                .withDefaultSingleChoice()
                .build();
        Question question2 = new QuestionBuilder()
                .withQuestionId(66L)
                .withDefaultSingleChoice()
                .build();

        Quiz quiz = new Quiz("Quiz 1", Stream.of(question1, question2).map(Question::getId).toList());

        assertThat(quiz.isLastQuestion(question1.getId()))
                .isFalse();
    }
}
