package com.example.quiz.domain.quiz;

import com.example.quiz.domain.Question;
import com.example.quiz.domain.port.QuestionRepository;
import java.util.List;

public class QuizFactory {

  private final QuestionRepository questionRepository;

  public QuizFactory(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  public InMemoryQuiz createQuiz() {
    final List<Question> questions = questionRepository.findAll();

    return new InMemoryQuiz(questions);
  }
}
