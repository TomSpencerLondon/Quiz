package com.example.quiz.adapter.in.questionloader;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;

public class QuestionFactory {

  public QuestionFactory() {
  }

  Question createQuestion(String questionText, String correctAnswer, String answer1,
      String answer2, String answer3, String answer4) {
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