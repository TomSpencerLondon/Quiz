package com.example.quiz.domain;

import com.example.quiz.QuizSession;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Quiz {

  private List<Question> questions = new ArrayList<>();

  public Quiz(Question... questions) {
    this.questions.addAll(asList(questions));
  }

  public Quiz(List<Question> questions) {
    this.questions = questions;
  }

  public List<Question> questions() {
    return questions;
  }

  public QuizSession start() {
    return new QuizSession(this);
  }
}
