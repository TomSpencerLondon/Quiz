package com.example.quiz.domain;

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
