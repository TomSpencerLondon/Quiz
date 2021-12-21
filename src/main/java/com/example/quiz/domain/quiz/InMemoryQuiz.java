package com.example.quiz.domain.quiz;

import com.example.quiz.domain.Question;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class InMemoryQuiz implements Quiz {

  private List<Question> questions = new ArrayList<>();

  public InMemoryQuiz(Question... questions) {
    this.questions.addAll(asList(questions));
  }

  public InMemoryQuiz(List<Question> questions) {
    this.questions = questions;
  }

  public List<Question> questions() {
    return questions;
  }

  public QuizSession start() {
    return new QuizSession(this);
  }
}
