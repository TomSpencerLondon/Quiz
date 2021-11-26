package com.example.quiz.adapter.in.web;

import com.example.quiz.adapter.port.repository.jpa.QuestionRepositoryJpaAdapter;
import com.example.quiz.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionService {
  private QuestionRepositoryJpaAdapter questionRepositoryJpaAdapter;

  @Autowired
  public QuestionService(QuestionRepositoryJpaAdapter questionRepositoryJpaAdapter) {
    this.questionRepositoryJpaAdapter = questionRepositoryJpaAdapter;
  }

  public Question add(Question question) {
    return questionRepositoryJpaAdapter.save(question);
  }
}
