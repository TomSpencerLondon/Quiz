package com.example.quiz.adapter.in.web;

import com.example.quiz.domain.Question;
import com.example.quiz.domain.port.QuestionRepository;
import com.example.quiz.domain.quiz.Quiz;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebQuiz implements Quiz {

  private QuestionRepository questionRepository;

  @Autowired
  public WebQuiz(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  @Transactional
  @Override
  public List<Question> questions() {
    return questionRepository.findAll();
  }
}
