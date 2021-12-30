package com.example.quiz.domain.quiz;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;

public class QuestionFactory {

  public static Question create(String questionText, String answer1, String answer2,
      String answer3,
      String answer4, String correctAnswer) {
    return new Question(questionText,
        new MultipleChoice(
            new Answer(correctAnswer),
            List.of(
                new Answer(answer1),
                new Answer(answer2), new Answer(answer3),
                new Answer(answer4))
        ));
  }
}
