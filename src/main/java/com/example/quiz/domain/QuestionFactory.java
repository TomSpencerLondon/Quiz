package com.example.quiz.domain;

import com.example.quiz.adapter.in.web.ChoiceForm;
import java.util.Arrays;
import java.util.List;

public class QuestionFactory {

  public static Question create(String questionText, ChoiceForm... choices) {
    Choice correctChoice = Arrays.stream(choices)
        .filter(ChoiceForm::isCorrectAnswer)
        .map(c -> new Choice(c.getChoice())).findFirst()
        .orElseThrow(() -> new NoCorrectChoiceSelected(
            String.format("No choices (%s,%s,%s,%s) are marked as correct", choices)));

    return new Question(questionText,
        new SingleChoice(
            correctChoice,
            List.of(
                new Choice(choices[0].getChoice()),
                new Choice(choices[1].getChoice()),
                new Choice(choices[2].getChoice()),
                new Choice(choices[3].getChoice()))
        ));
  }

  public static Question create(String questionText, String choice1, String choice2,
      String choice3,
      String choice4, String answer) {
    return new Question(questionText,
        new SingleChoice(
            new Choice(answer),
            List.of(
                new Choice(choice1),
                new Choice(choice2),
                new Choice(choice3),
                new Choice(choice4))
        ));
  }


}
