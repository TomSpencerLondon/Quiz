package com.example.quiz.adapter.out.jpa;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.ChoiceBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChoiceTransformerTest {
    final ChoiceTransformer choiceTransformer = new ChoiceTransformer();

    @Test
    void choiceToChoiceDboTransformsCorrectly() {
        // given
        Choice choice = new ChoiceBuilder()
                .withCorrectChoice()
                .build();

        // when
        ChoiceDbo choiceDbo = choiceTransformer.toChoiceDbo(choice);

        // then
        assertThat(choiceDbo.getChoiceText())
                .isEqualTo("Answer 1");
        assertThat(choiceDbo.isCorrect())
                .isTrue();
    }

    @Test
    void choiceDboToChoiceTransformsCorrectly() {
        // given
        ChoiceDbo choiceDbo = new ChoiceDboBuilder()
                .withId(1L)
                .withText("choice 1")
                .withCorrect(true)
                .build();

        // when
        Choice choice = choiceTransformer.toChoice(choiceDbo);

        // then
        assertThat(choice.text())
                .isEqualTo("choice 1");
        assertThat(choice.isCorrect())
                .isTrue();
        assertThat(choice.getId().id())
                .isEqualTo(1L);
    }
}