package com.example.quiz.domain.quiz;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.SingleChoice;
import java.util.List;

public class MultipleChoiceQuestionFactory {

  public static Question createMultipleChoiceQuestion() {
    Question question = new Question(
        "Question 1",
        new SingleChoice(new Choice("Answer 1"),
            List.of(new Choice("Answer 1"), new Choice("Answer 2"))));
    return question;
  }
}
