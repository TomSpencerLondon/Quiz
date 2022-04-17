package com.example;

import com.example.quiz.adapter.in.web.edit.AddQuestionForm;
import com.example.quiz.adapter.in.web.edit.ChoiceForm;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;

class ChoiceValidatorTest {

    public static final ConstraintValidatorContext DUMMY_CONSTRAINT_VALIDATOR_CONTEXT = null;

    @Test
    void shouldRejectSingleChoiceWithZeroCorrectAnswers() {
        // Given
        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                new ChoiceForm("a1", false),
                new ChoiceForm("a2", false),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false), "single");
        ChoiceValidator choiceValidator = new ChoiceValidator();

        // Then
        assertThat(choiceValidator.isValid(addQuestionForm, DUMMY_CONSTRAINT_VALIDATOR_CONTEXT))
                .isFalse();
    }

    @Test
    void shouldRejectSingleChoiceWithTwoCorrectAnswers() {
        // Given
        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                new ChoiceForm("a1", true),
                new ChoiceForm("a2", true),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false), "single");
        ChoiceValidator choiceValidator = new ChoiceValidator();

        // Then
        assertThat(choiceValidator.isValid(addQuestionForm, DUMMY_CONSTRAINT_VALIDATOR_CONTEXT))
                .isFalse();
    }

    @Test
    void shouldAllowSingleChoiceWithOneCorrectAnswer() {
        // Given
        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                new ChoiceForm("a1", true),
                new ChoiceForm("a2", false),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false), "single");

        ChoiceValidator choiceValidator = new ChoiceValidator();
        assertThat(choiceValidator.isValid(addQuestionForm, DUMMY_CONSTRAINT_VALIDATOR_CONTEXT))
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

        // Then
        assertThat(choiceValidator.isValid(addQuestionForm, DUMMY_CONSTRAINT_VALIDATOR_CONTEXT))
                .isFalse();
    }

    @Test
    void shouldAllowMultiplechoiceWithAtLeastTwoCorrectAnswers() {
        // Given
        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                new ChoiceForm("a1", true),
                new ChoiceForm("a2", true),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false), "multiple");
        ChoiceValidator choiceValidator = new ChoiceValidator();

        // Then
        assertThat(choiceValidator.isValid(addQuestionForm, DUMMY_CONSTRAINT_VALIDATOR_CONTEXT))
                .isTrue();
    }
}