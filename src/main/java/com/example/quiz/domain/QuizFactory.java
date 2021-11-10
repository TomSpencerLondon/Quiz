package com.example.quiz.domain;

import com.example.quiz.domain.port.QuestionRepository;
import java.util.List;

public class QuizFactory {

  private final QuestionRepository questionRepository;

  public QuizFactory(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  public Quiz createQuiz() {
    final List<Question> questions = questionRepository.findAll();

    return new Quiz(questions);
  }
}
