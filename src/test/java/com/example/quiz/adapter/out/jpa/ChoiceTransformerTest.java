package com.example.quiz.adapter.out.jpa;

import com.example.quiz.domain.Choice;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChoiceTransformerTest {

    @Test
    void choiceToChoiceDboTransformsCorrectly() {
        Choice choice = new Choice(2L, "choice 2", true);

        ChoiceDbo choiceDbo = ChoiceTransformer.toChoiceDbo(choice);

        assertThat(choiceDbo.getId())
                .isEqualTo(2L);
        assertThat(choiceDbo.getChoiceText())
                .isEqualTo("choice 2");
        assertThat(choiceDbo.isCorrect())
                .isTrue();
    }

    @Test
    void choiceDboToChoiceTransformsCorrectly() {
        ChoiceDbo choiceDbo = new ChoiceDbo();
        choiceDbo.setId(1L);
        choiceDbo.setChoiceText("choice 1");
        choiceDbo.setCorrect(true);

        Choice choice = ChoiceTransformer.toChoice(choiceDbo);

        assertThat(choice.text())
                .isEqualTo("choice 1");
        assertThat(choice.isCorrect())
                .isTrue();
        assertThat(choice.getId())
                .isEqualTo(1L);
    }
}