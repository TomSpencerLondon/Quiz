package com.example.quiz.domain;

import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseTest {

    @Test
    void correctResponseIsMarkedAsCorrect() {
        Question singleChoiceQuestion = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();

        Choice answer = new Choice("Answer 1", true);

        Response response = new Response(singleChoiceQuestion, answer);

        assertThat(response.isCorrect())
                .isTrue();
    }

    @Test
    void wrongResponseIsMarkedAsIncorrect() {
        Question singleChoiceQuestion = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();

        Choice answer = new Choice("Answer 2");

        Response response = new Response(singleChoiceQuestion, answer);

        assertThat(response.isCorrect())
                .isFalse();
    }
}