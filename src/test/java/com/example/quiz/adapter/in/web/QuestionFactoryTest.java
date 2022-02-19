package com.example.quiz.adapter.in.web;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionFactoryTest {

    @Test
    void shouldCreateSingleChoiceQuestionWhenOnlyOneChoiceIsCorrect() {
        // Arrange
        String questionText = "Question 1";
        ChoiceForm correctChoiceForm = new ChoiceForm("Answer 1", true);
        ChoiceForm choiceForm1 = new ChoiceForm("Answer 2", false);
        ChoiceForm choiceForm2 = new ChoiceForm("Answer 3", false);
        ChoiceForm choiceForm3 = new ChoiceForm("Answer 4", false);

        // Act
        Question questionResult = QuestionFactory
                .create(questionText, correctChoiceForm, choiceForm1, choiceForm2, choiceForm3);

        assertThat(questionResult.isSingleChoice())
                .isTrue();

        Assertions.assertThat(questionResult.isCorrectAnswer(new Choice(correctChoiceForm.getChoice())))
                  .isTrue();
    }

    @Test
    void singleChoiceMustHaveAtLeastOneCorrectAnswer() {
        // Given

        String questionText = "Question 1";
        ChoiceForm choiceForm = new ChoiceForm("Answer 1", false);
        ChoiceForm choiceForm1 = new ChoiceForm("Answer 2", false);
        ChoiceForm choiceForm2 = new ChoiceForm("Answer 3", false);
        ChoiceForm choiceForm3 = new ChoiceForm("Answer 4", false);

        // when + then
        assertThatThrownBy(() -> {
            QuestionFactory
                    .create(questionText, choiceForm, choiceForm1, choiceForm2, choiceForm3);
        }).isInstanceOf(NoCorrectChoiceSelected.class)
          .hasMessage(String.format(
                  "No choices (%s,%s,%s,%s) are marked as correct",
                  choiceForm, choiceForm1, choiceForm2, choiceForm3));

    }

    @Test
    void singleChoiceMustHaveNoMoreThanOneCorrectAnswer() {
        // Given

        String questionText = "Question 1";
        ChoiceForm choiceForm = new ChoiceForm("Answer 1", true);
        ChoiceForm choiceForm1 = new ChoiceForm("Answer 2", true);
        ChoiceForm choiceForm2 = new ChoiceForm("Answer 3", false);
        ChoiceForm choiceForm3 = new ChoiceForm("Answer 4", false);

        // when + then
        assertThatThrownBy(() -> {
            QuestionFactory
                    .create(questionText, choiceForm, choiceForm1, choiceForm2, choiceForm3);
        }).isInstanceOf(TooManyCorrectChoicesSelected.class)
          .hasMessage(String.format(
                  "Too many choices (%s,%s,%s,%s) are marked as correct",
                  choiceForm, choiceForm1, choiceForm2, choiceForm3));

    }
}
