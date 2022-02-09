package com.example.quiz.domain;

import static java.util.function.Predicate.not;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuizSession {

  private final Iterator<Question> iterator;
  private final List<Question> questions;

  private final List<Response> responses = new ArrayList<>();
  private Question question;

  public QuizSession(Quiz quiz) {
    questions = quiz.questions();
    if (questions.isEmpty()) {
      throw new IllegalArgumentException();
    }
    iterator = questions.iterator();
    question = iterator.next();
  }

  public Question question() {
    return question;
  }

  public void respondWith(Choice choice) {
    Response response = new Response(question, choice);
    responses.add(response);
    if (iterator.hasNext()) {
      question = iterator.next();
    }
  }

  public boolean isFinished() {
    return responses.size() == questions.size();
  }

  public int correctResponsesCount() {
    return Math.toIntExact(responses.stream()
        .filter(Response::isCorrect)
        .count());
  }

  public int incorrectResponsesCount() {
    return Math.toIntExact(responses.stream()
        .filter(not(Response::isCorrect))
        .count());
  }

  public Grade grade() {
    return new Grade(responses, correctResponsesCount(), incorrectResponsesCount());
  }

}
