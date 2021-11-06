package com.example.quiz.adapter.in.questionloader;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionLoader {

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

    return new Question(list.get(0), new MultipleChoice(new Answer(list.get(list.size() - 1)), List.of(
        new Answer(list.get(1)),
        new Answer(list.get(2)),
        new Answer(list.get(3)),
        new Answer(list.get(4)))));
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
