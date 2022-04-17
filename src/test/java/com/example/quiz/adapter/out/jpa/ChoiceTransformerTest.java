package com.example.quiz.adapter.out.jpa;

import com.example.quiz.domain.Choice;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChoiceTransformerTest {
    final ChoiceTransformer choiceTransformer = new ChoiceTransformer();

    @Test
    void choiceToChoiceDboTransformsCorrectly() {
        // given
        Choice choice = new Choice("choice 2", true);

        // when
        ChoiceDbo choiceDbo = choiceTransformer.toChoiceDbo(choice);

        // then
        assertThat(choiceDbo.getChoiceText())
                .isEqualTo("choice 2");
        assertThat(choiceDbo.isCorrect())
                .isTrue();
    }

    @Test
    void choiceDboToChoiceTransformsCorrectly() {
        // given
        ChoiceDbo choiceDbo = new ChoiceDbo();
        choiceDbo.setId(1L);
        choiceDbo.setChoiceText("choice 1");
        choiceDbo.setCorrect(true);

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