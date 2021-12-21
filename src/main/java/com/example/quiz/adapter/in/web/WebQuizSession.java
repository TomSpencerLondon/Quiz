package com.example.quiz.adapter.in.web;

import com.example.quiz.domain.Question;
import com.example.quiz.domain.Response;
import com.example.quiz.domain.ResponseStatus;
import com.example.quiz.domain.quiz.QuizSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebQuizSession implements QuizSession {

  private WebQuiz webQuiz;
  private final Iterator<Question> iterator;
  private List<Response> responses = new ArrayList<>();
  private Response lastResponse;

  @Autowired
  public WebQuizSession(WebQuiz webQuiz) {
    this.webQuiz = webQuiz;
    this.iterator = this.webQuiz.questions().iterator();
  }

  @Override
  public Question question() {
    return iterator.next();
  }

  @Override
  public void respondWith(String text, Question question) {
    lastResponse = new Response(text, question);
    responses.add(lastResponse);
  }

  @Override
  public ResponseStatus lastResponseStatus() {
    return lastResponse.status();
  }
}
