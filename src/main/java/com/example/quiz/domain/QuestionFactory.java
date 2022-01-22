package com.example.quiz.domain;

import java.util.List;

public class QuestionFactory {

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
