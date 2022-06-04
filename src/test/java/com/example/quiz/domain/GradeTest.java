package com.example.quiz.domain;

import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GradeTest {

    @Test
    void gradePercentCalculatedCorrectly() {
        // Given
        List<Response> responses = createResponses();

        // When
        final Grade grade = new Grade(responses, 1, 2);

        // Then
        assertThat(grade.percent())
                .isEqualTo(33);
    }

    @Test
    void returnsCorrectAndIncorrectValues() {
        // Given
        List<Response> responses = createResponses();

        // When
        final Grade grade = new Grade(responses, 1, 2);

        // Then
        assertThat(grade.correct())
                .isEqualTo(1);

        assertThat(grade.incorrect())
                .isEqualTo(2);
    }

    private List<Response> createResponses() {
        Question q1 = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        Question q2 = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        Question q3 = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        Choice choice = new Choice(ChoiceId.of(1L), "Answer 1", true);
        Response r1 = new Response(q1.getId(), q1.isCorrectAnswer(choice), choice);
        Response r2 = SingleChoiceQuestionTestFactory.createSingleChoiceQuestionIncorrectResponse(q2);
        Response r3 = SingleChoiceQuestionTestFactory.createSingleChoiceQuestionIncorrectResponse(q3);

        List<Response> responses = List.of(r1, r2, r3);
        return responses;
    }

}