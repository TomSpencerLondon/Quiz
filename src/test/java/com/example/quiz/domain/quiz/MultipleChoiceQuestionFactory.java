package com.example.quiz.domain.quiz;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;

public class MultipleChoiceQuestionFactory {

  public static Question createMultipleChoiceQuestion() {
    Question question = new Question(
        "Question 1",
        new MultipleChoice(new Choice("Answer 1"),
            List.of(new Choice("Answer 1"), new Choice("Answer 2"))));
    return question;
  }
}
