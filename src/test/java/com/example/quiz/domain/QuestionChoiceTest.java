package com.example.quiz.domain;

import com.example.quiz.hexagon.domain.Choice;
import com.example.quiz.hexagon.domain.Question;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionChoiceTest {

    @Test
    void knowsOneAnswer() {
        // Given
        Question question = new QuestionBuilder()
                .withQuestionId(1L)
                .withSingleChoice(new ChoiceBuilder().withCorrectChoice().asList())
                .build();

        // When
        List<Choice> choices = question.choices();

        // Then
        Choice[] expected = new ChoiceBuilder().withCorrectChoice().asArray();
        assertThat(choices).containsExactly(expected);
    }

    @Test
    void knowsSeveralAnswers() {
        // Given

        List<Choice> choices = new ChoiceBuilder().withCorrectChoice()
                                                  .withIncorrectChoice()
                                                  .withIncorrectChoice()
                                                  .withIncorrectChoice().asList();
        Question question = new QuestionBuilder().withSingleChoice(choices).build();

        // When
        List<Choice> result = question.choices();

        // Then
        Choice[] expected = new ChoiceBuilder()
                .withCorrectChoice()
                .withIncorrectChoice()
                .withIncorrectChoice()
                .withIncorrectChoice()
                .asArray();
        assertThat(result)
                .containsExactly(expected);
    }

    @Test
    void knowsCorrectAnswer() {
        // Given
        Question question = new QuestionBuilder()
                .withSingleChoice(
                        new ChoiceBuilder().withCorrectChoice().withIncorrectChoice().asList())
                .build();

        // When
        List<Choice> choices = new ChoiceBuilder().withCorrectChoice().withIncorrectChoice().asList();
        final boolean correctAnswer = question.isCorrectAnswer(choices.get(0));

        // Then
        assertThat(correctAnswer)
                .isTrue();
    }

}
