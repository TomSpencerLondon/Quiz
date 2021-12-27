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
  private final Iterator<Question> iterator;

  private List<Response> responses = new ArrayList<>();
  private Response lastResponse;
  private Question question;

  public QuizSession(Quiz quiz) {
    if (quiz.questions().isEmpty()) {
      throw new IllegalArgumentException();
    }
    iterator = quiz.questions().iterator();
    question = iterator.next();
  }

  public Question question() {
    if (lastResponse == null || !lastResponse.question().equals(question)){
      return question;
    }
    question = iterator.next();
    return question;
  }

  public void respondWith(String text, Question question) {
    lastResponse = new Response(text, question);
    responses.add(lastResponse);
  }

  public boolean isFinished() {
    if (lastResponse == null || !lastResponse.question().equals(question)){
      return false;
    }

    return !iterator.hasNext();
  }

  public ResponseStatus lastResponseStatus() {
    return lastResponse.status();
  }

  public long correctResponsesCount() {
    return responseCountFor(ResponseStatus.CORRECT);
  }

  public long incorrectResponsesCount() {
    return responseCountFor(ResponseStatus.INCORRECT);
  }

  private long responseCountFor(ResponseStatus status) {
    return responses.stream()
        .filter(r -> r.status().equals(status))
        .count();
  }

  public Grade grade() {
    return new Grade(responses.size(),
        new FinalMark(correctResponsesCount(), incorrectResponsesCount()));
  }
}
