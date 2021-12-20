package com.example.quiz.adapter.in.web;

import com.example.quiz.adapter.out.repository.jpa.QuestionRepositoryJpaAdapter;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.port.QuestionRepository;
import com.example.quiz.domain.quiz.Quiz;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuizWeb implements Quiz {

  private QuestionRepository questionRepositoryJpaAdapter;

  @Autowired
  public QuizWeb(QuestionRepository questionRepositoryJpaAdapter) {
    this.questionRepositoryJpaAdapter = questionRepositoryJpaAdapter;
  }

  @Transactional
  @Override
  public List<Question> questions() {
    return questionRepositoryJpaAdapter.findAll();
  }
}
