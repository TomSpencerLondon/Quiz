package com.example.quiz.adapter.in.web;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AddQuestionFormTest {
    @Test
    void transformsChoicesIntoListOfStrings() {
        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                new ChoiceForm("a1", true),
                new ChoiceForm("a2", false),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false), "single");

        List<String> choices = addQuestionForm.transformToChoices();

        assertThat(choices)
                .containsExactly(
                        "a1",
                        "a2",
                        "a3",
                        "a4"
                );
    }

    @Test
    void transformToCorrectChoices() {
        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                new ChoiceForm("a1", true),
                new ChoiceForm("a2", false),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false), "single");

        List<String> correctChoices = addQuestionForm.transformToCorrectChoices();

        assertThat(correctChoices)
                .containsExactly("a1");
    }
}