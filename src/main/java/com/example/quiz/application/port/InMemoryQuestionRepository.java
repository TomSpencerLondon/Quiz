package com.example.quiz.application.port;

import com.example.quiz.domain.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryQuestionRepository implements
    QuestionRepository {

  private List<Question> questions;
  private AtomicLong counter = new AtomicLong();

  public InMemoryQuestionRepository() {
    questions = new ArrayList<>();
  }

  @Override
  public Question save(Question question) {
    if (question.getId() == null) {
      question.setId(counter.getAndIncrement());
    }
    questions.add(question);
    return question;
  }

  @Override
  public List<Question> findAll() {
    return questions;
  }
}
