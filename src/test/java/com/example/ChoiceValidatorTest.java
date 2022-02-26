package com.example;

import com.example.quiz.adapter.in.web.edit.AddQuestionForm;
import com.example.quiz.adapter.in.web.edit.ChoiceForm;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChoiceValidatorTest {

    @Test
    void shouldRejectSingleChoiceWithZeroCorrectAnswers() {
        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                new ChoiceForm("a1", false),
                new ChoiceForm("a2", false),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false), "single");

        ChoiceValidator choiceValidator = new ChoiceValidator();
        assertThat(choiceValidator.isValid(addQuestionForm, null))
                .isFalse();
    }

    @Test
    void shouldRejectSingleChoiceWithTwoCorrectAnswers() {
        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                new ChoiceForm("a1", true),
                new ChoiceForm("a2", true),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false), "single");

        ChoiceValidator choiceValidator = new ChoiceValidator();
        assertThat(choiceValidator.isValid(addQuestionForm, null))
                .isFalse();
    }

    @Test
    void shouldAllowSingleChoiceWithOneCorrectAnswer() {
        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                new ChoiceForm("a1", true),
                new ChoiceForm("a2", false),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false), "single");

        ChoiceValidator choiceValidator = new ChoiceValidator();
        assertThat(choiceValidator.isValid(addQuestionForm, null))
                .isTrue();

    }

    @Test
    void shouldRejectMultipleChoiceWithOneCorrectAnswer() {
        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                new ChoiceForm("a1", true),
                new ChoiceForm("a2", false),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false), "multiple");

        ChoiceValidator choiceValidator = new ChoiceValidator();
        assertThat(choiceValidator.isValid(addQuestionForm, null))
                .isFalse();
    }

    @Test
    void shouldAllowMultiplechoiceWithAtLeastTwoCorrectAnswers() {
        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                new ChoiceForm("a1", true),
                new ChoiceForm("a2", true),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false), "multiple");

        ChoiceValidator choiceValidator = new ChoiceValidator();
        assertThat(choiceValidator.isValid(addQuestionForm, null))
                .isTrue();
    }
}