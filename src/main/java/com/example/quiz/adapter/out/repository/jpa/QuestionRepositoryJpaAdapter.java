package com.example.quiz.adapter.out.repository.jpa;

import com.example.quiz.domain.port.QuestionRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepositoryJpaAdapter implements QuestionRepository {

  private final QuestionJpaRepository questionJpaRepository;
  private final QuestionTransformer questionTransformer;

  @Autowired
  public QuestionRepositoryJpaAdapter(QuestionJpaRepository questionJpaRepository,
      QuestionTransformer questionTransformer) {
    this.questionJpaRepository = questionJpaRepository;
    this.questionTransformer = questionTransformer;
  }

  @Override
  public com.example.quiz.domain.Question save(com.example.quiz.domain.Question question) {
    Question questionDto = questionTransformer.toQuestionDto(question);
    return questionTransformer.toQuestion(questionJpaRepository.save(questionDto));
  }

  @Transactional
  @Override
  public List<com.example.quiz.domain.Question> findAll() {
    List<Question> questions = questionJpaRepository.findAll();
    return questions.stream().map(questionTransformer::toQuestion).collect(Collectors.toList());
  }
}
