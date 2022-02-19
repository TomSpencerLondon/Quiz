package com.example.quiz.domain;

import com.example.quiz.adapter.in.web.ChoiceForm;
import java.util.Arrays;
import java.util.List;

public class QuestionFactory {

  public static Question create(String questionText, ChoiceForm... choices) {
    checkForMoreThanOneCorrectChoice(choices);
    Choice correctChoice = extractCorrectChoiceFrom(choices);
    SingleChoice singleChoice = new SingleChoice(correctChoice, extractChoicesFrom(choices));
    return new Question(questionText, singleChoice);
  }

  private static List<Choice> extractChoicesFrom(ChoiceForm[] choices) {
    return Arrays.stream(choices).map(c -> new Choice(c.getChoice())).toList();
  }

  private static Choice extractCorrectChoiceFrom(ChoiceForm[] choices) {
    return Arrays.stream(choices)
        .filter(ChoiceForm::isCorrectAnswer)
        .map(c -> new Choice(c.getChoice()))
        .findFirst()
        .orElseThrow(() -> new NoCorrectChoiceSelected(choices));
  }

  private static void checkForMoreThanOneCorrectChoice(ChoiceForm[] choices) {
    long count = Arrays.stream(choices).filter(ChoiceForm::isCorrectAnswer).count();
    if (count > 1) {
      throw new TooManyCorrectChoicesSelected(choices);
    }
  }

  public static Question create(
      String questionText,
      String choice1,
      String choice2,
      String choice3,
      String choice4,
      String answer) {
    return new Question(
        questionText,
        new SingleChoice(
            new Choice(answer),
            List.of(
                new Choice(choice1),
                new Choice(choice2),
                new Choice(choice3),
                new Choice(choice4))));
  }
}
