package com.example.quiz.adapter.in.web.edit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.quiz.hexagon.domain.Question;
import com.example.quiz.hexagon.domain.domain.QuestionBuilder;

import java.util.List;

import org.junit.jupiter.api.Test;

class AddQuestionFormTest {
    @Test
    void transformsChoicesIntoListOfStrings() {
        Question question = new QuestionBuilder()
                .withDefaultSingleChoice()
                .build();

        AddQuestionForm addQuestionForm = new AddQuestionFormBuilder()
                .withQuestion(question)
                .build();

        List<String> choices = addQuestionForm.transformToChoices();

        assertThat(choices)
                .containsExactly(
                        "Answer 1",
                        "Answer 2",
                        "Answer 3"
                );
    }

    @Test
    void transformToCorrectChoicesForSingleChoice() {
        Question question = new QuestionBuilder()
                .withDefaultSingleChoice()
                .build();
        AddQuestionForm addQuestionForm = new AddQuestionFormBuilder()
                .withQuestion(question)
                .build();

        List<String> correctChoices = addQuestionForm.transformToCorrectChoices();

        assertThat(correctChoices)
                .containsExactly("Answer 1");
    }

    @Test
    void transformToCorrectChoicesForMultipleChoice() {
        Question question = new QuestionBuilder()
                .withDefaultMultipleChoice()
                .build();
        AddQuestionForm addQuestionForm = new AddQuestionFormBuilder()
                .withQuestion(question)
                .build();

        List<String> correctChoices = addQuestionForm.transformToCorrectChoices();

        assertThat(correctChoices)
                .containsExactly("Answer 1", "Answer 2");
    }

    @Test
    void throwsErrorForTwoCorrectChoicesForSingleChoice() {
        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                "single", new ChoiceForm("a1", true),
                new ChoiceForm("a2", true),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false));

        assertThatThrownBy(addQuestionForm::transformToCorrectChoices)
                .isInstanceOf(TooManyCorrectChoicesSelected.class);
    }

    @Test
    void throwsErrorForOneCorrectChoiceForMultipleChoice() {
        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                "multiple", new ChoiceForm("a1", true),
                new ChoiceForm("a2", false),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false));

        assertThatThrownBy(addQuestionForm::transformToCorrectChoices)
                .isInstanceOf(TooFewCorrectChoicesSelected.class);
    }
}