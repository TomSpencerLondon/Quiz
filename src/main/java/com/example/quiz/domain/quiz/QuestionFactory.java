package com.example.quiz.domain.quiz;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;

public class QuestionFactory {

  public static Question create(String questionText, String answer1, String answer2,
      String answer3,
      String answer4, String correctAnswer) {
    return new Question(questionText,
        new MultipleChoice(
            new Choice(correctAnswer),
            List.of(
                new Choice(answer1),
                new Choice(answer2), new Choice(answer3),
                new Choice(answer4))
        ));
  }
}
