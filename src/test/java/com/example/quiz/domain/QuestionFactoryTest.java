package com.example.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.quiz.adapter.in.web.ChoiceForm;
import org.junit.jupiter.api.Test;

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

    assertThat(questionResult.isCorrectAnswer(new Choice(correctChoiceForm.getChoice())))
        .isTrue();
  }

  @Test
  void singleChoiceMustHaveOneCorrectAnswer() {
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
    }).isInstanceOf(IllegalArgumentException.class)
        .hasMessage(String.format(
            "No choices (%s,%s,%s,%s) are marked as correct",
            choiceForm, choiceForm1, choiceForm2, choiceForm3));

  }
}
