package com.example.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.adapter.in.web.ChoiceForm;
import org.junit.jupiter.api.Test;

public class QuestionFactoryTest {

  @Test
  void shouldCreateSingleChoiceQuestionWhenOnlyOneChoiceIsCorrect() {
    // Arrange
    String questionText = "Question 1";
    ChoiceForm choiceForm1 = new ChoiceForm("Answer 1", true);
    ChoiceForm choiceForm2 = new ChoiceForm("Answer 2", false);
    ChoiceForm choiceForm3 = new ChoiceForm("Answer 3", false);
    ChoiceForm choiceForm4 = new ChoiceForm("Answer 4", false);

    // Act
    Question questionResult = QuestionFactory
        .create(questionText, choiceForm1, choiceForm2, choiceForm3, choiceForm4);

    assertThat(questionResult.isSingleChoice())
        .isTrue();
  }
}
