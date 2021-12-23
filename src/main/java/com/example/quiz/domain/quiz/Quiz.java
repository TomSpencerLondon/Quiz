package com.example.quiz.domain.quiz;

import com.example.quiz.domain.Question;
import com.example.quiz.domain.port.QuestionRepository;
import java.util.List;
import javax.transaction.Transactional;

public class Quiz {

  private final QuestionRepository questionRepository;

  public Quiz(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  public List<Question> questions() {
    return this.questionRepository.findAll();
  }

  public QuizSession start() {
    return new QuizSession(this);
  }
}
