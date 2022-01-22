package com.example.quiz.application;

import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.domain.Question;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionService {
  private QuestionRepository questionRepository;

  @Autowired
  public QuestionService(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  public Question add(Question question) {
    return questionRepository.save(question);
  }

  @Transactional
  public List<Question> findAll() {
    return questionRepository.findAll();
  }
}
