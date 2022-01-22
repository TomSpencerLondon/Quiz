package com.example.quiz.adapter.in.questionloader;

import com.example.quiz.domain.Question;
import java.util.List;

public class QuestionFactory {

  public static Question createQuestion(List<String> lines) {
    String questionText = lines.get(0);
    String answer1 = lines.get(1);
    String answer2 = lines.get(2);
    String answer3 = lines.get(3);
    String answer4 = lines.get(4);
    String correctAnswer = lines.get(5);

    return com.example.quiz.domain.QuestionFactory.create(questionText, answer1, answer2, answer3, answer4, correctAnswer);
  }

}