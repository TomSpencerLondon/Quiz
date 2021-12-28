package com.example.quiz.domain.quiz;

import com.example.quiz.domain.FinalMark;
import com.example.quiz.domain.Grade;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Response;
import com.example.quiz.domain.ResponseStatus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuizSession {

  private final Quiz quiz;
  private Iterator<Question> iterator;
  private List<Question> questions;

  private List<Response> responses = new ArrayList<>();
  private Response lastResponse;
  private Question question;

  public QuizSession(Quiz quiz) {
    if (quiz.questions().isEmpty()) {
      throw new IllegalArgumentException();
    }
    this.quiz = quiz;
    questions = quiz.questions();
    iterator = questions.iterator();
    question = iterator.next();
  }

  public Question question() {
    return question;
  }

  public void respondWith(String text, Question question) {
    lastResponse = new Response(text, question);
    responses.add(lastResponse);
    if (iterator.hasNext()) {
      this.question = iterator.next();
    }
  }

  public boolean isFinished() {
    return responses.size() == questions.size();
  }

  public ResponseStatus lastResponseStatus() {
    return lastResponse.status();
  }

  public int correctResponsesCount() {
    return responseCountFor(ResponseStatus.CORRECT);
  }

  public int incorrectResponsesCount() {
    return responseCountFor(ResponseStatus.INCORRECT);
  }

  private int responseCountFor(ResponseStatus status) {
    return Math.toIntExact(responses.stream()
        .filter(r -> r.status().equals(status))
        .count());
  }

  public Grade grade() {
    return new Grade(responses.size(),
        new FinalMark(correctResponsesCount(), incorrectResponsesCount()));
  }


  public void restart() {
    responses = new ArrayList<>();
    questions = this.quiz.questions();
    iterator = questions.iterator();
    question = iterator.next();
  }
}
