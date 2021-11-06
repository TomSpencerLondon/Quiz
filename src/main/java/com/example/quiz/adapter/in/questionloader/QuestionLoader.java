package com.example.quiz.adapter.in.questionloader;

import com.example.quiz.domain.Question;
import java.util.Arrays;
import java.util.List;

public class QuestionLoader {

  private final QuestionFactory questionFactory;

  public QuestionLoader() {
    this.questionFactory = new QuestionFactory();
  }

  public List<Question> parse(String quizText) {
    return splitToQuestions(quizText).stream()
        .map(this::parseQuestion)
        .toList();
  }

  private List<String> splitToQuestions(String quizText) {
    return Arrays.stream(quizText.split("---")).toList();
  }

  private Question parseQuestion(String questionText) {
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
