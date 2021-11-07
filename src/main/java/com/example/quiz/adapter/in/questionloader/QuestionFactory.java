package com.example.quiz.adapter.in.questionloader;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;

public class QuestionFactory {

  public QuestionFactory() {
  }

  Question createQuestion(List<String> lines) {
    String questionText = lines.get(0);
    String answer1 = lines.get(2);
    String answer2 = lines.get(3);
    String answer3 = lines.get(4);
    String answer4 = lines.get(5);
    String correctAnswer = lines.get(5);

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