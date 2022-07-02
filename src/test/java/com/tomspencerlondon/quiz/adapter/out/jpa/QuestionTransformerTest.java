package com.tomspencerlondon.quiz.adapter.out.jpa;
import com.tomspencerlondon.quiz.hexagon.domain.Question;
import com.tomspencerlondon.quiz.hexagon.domain.QuestionId;
import com.tomspencerlondon.quiz.hexagon.domain.domain.QuestionBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionTransformerTest {
    final ChoiceTransformer choiceTransformer = new ChoiceTransformer();
    final QuestionTransformer questionTransformer = new QuestionTransformer(choiceTransformer);

    @Test
    void questionDboToSingleChoiceQuestion() {
        // Given
        Question expected = new QuestionBuilder()
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .build();

        QuestionDbo questionDbo = new QuestionDboBuilder().from(expected).build();

        // When
        final Question question = questionTransformer.toQuestion(questionDbo);

        // Then
        assertThat(question)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void singleChoiceQuestionToQuestionDbo() {
        // Given

        Question question = new QuestionBuilder().withDefaultSingleChoiceWithoutIds().build();

        QuestionDbo questionDbo = new QuestionDboBuilder().from(question).build();

        // When
        final QuestionDbo result = questionTransformer.toQuestionDbo(question);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(questionDbo);

    }

    @Test
    void multipleChoiceQuestionToQuestionDbo() {
        // Given
        Question question = new QuestionBuilder()
                .withDefaultMultipleChoiceWithoutIds()
                .build();

        QuestionDbo questionDbo = new QuestionDboBuilder().from(question).build();

        // When
        final QuestionDbo result = questionTransformer.toQuestionDbo(question);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(questionDbo);
    }

    @Test
    void questionDboToMultipleChoiceQuestion() {
        // Given
        Question question = new QuestionBuilder().withDefaultMultipleChoice().build();
        question.setId(QuestionId.of(1L));

        QuestionDbo questionDbo = new QuestionDboBuilder().from(question).build();

        // When
        final Question result = questionTransformer.toQuestion(questionDbo);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(question);
        assertThat(result.isSingleChoice())
                .isFalse();
    }
}