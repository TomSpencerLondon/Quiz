package com.example.quiz.adapter.out.repository.jpa;

import com.example.quiz.domain.port.QuestionRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepositoryJpaAdapter implements QuestionRepository {

  private final QuestionJpaRepository questionJpaRepository;
  private final QuestionDtoTransformer questionDtoTransformer;

  @Autowired
  public QuestionRepositoryJpaAdapter(QuestionJpaRepository questionJpaRepository,
      QuestionDtoTransformer questionDtoTransformer) {
    this.questionJpaRepository = questionJpaRepository;
    this.questionDtoTransformer = questionDtoTransformer;
  }

  @Override
  public com.example.quiz.domain.Question save(com.example.quiz.domain.Question question) {
    Question questionDto = questionDtoTransformer.toQuestionDto(question);
    return questionDtoTransformer.toQuestion(questionJpaRepository.save(questionDto));
  }

  @Override
  public List<com.example.quiz.domain.Question> findAll() {
    List<Question> questions = questionJpaRepository.findAll();
    return questions.stream().map(questionDtoTransformer::toQuestion).collect(Collectors.toList());
  }
}
