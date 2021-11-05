package com.example.quiz.adapter.in.questionloader;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionLoader {

  public Question parse(String questionText) {
    List<String> list = Arrays.stream(questionText.split("\n"))
        .filter(value -> value != null && value.length() > 0).toList();
    return new Question(list.get(0), new MultipleChoice(new Answer(list.get(list.size() - 1)), List.of(new Answer(""))));
  }
}
