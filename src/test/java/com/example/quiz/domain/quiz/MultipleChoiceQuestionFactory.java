package com.example.quiz.domain.quiz;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;

public class MultipleChoiceQuestionFactory {

  public static Question createMultipleChoiceQuestion() {
    Question question = new Question(
        "Question 1",
        new MultipleChoice(new Answer("Answer 1"),
            List.of(new Answer("Answer 1"), new Answer("Answer 2"))));
    return question;
  }
}
