package com.example.quiz.adapter.in.questionloader;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionLoader {

  private final QuestionFactory questionFactory;

  public QuestionLoader() {
    this.questionFactory = new QuestionFactory();
  }

  public List<Question> parse(String input) {
    List<Question> result = new ArrayList<>();
    List<String> list = Arrays.stream(input.split("---")).toList();
    for (String questionText : list) {
      result.add(question(questionText));
    }
    return result;
  }

  private Question question(String questionText) {
    List<String> list = notEmptyLinesFrom(questionText);

    return questionFactory
        .createQuestion(list.get(0), list.get(list.size() - 1), list.get(1),
        list.get(2), list.get(3), list.get(4));
  }

  private List<String> notEmptyLinesFrom(String questionText) {
    return questionText.lines()
        .filter(this::isNotEmpty)
        .toList();
  }

  private boolean isNotEmpty(String value) {
    return value.length() > 0;
  }
}
