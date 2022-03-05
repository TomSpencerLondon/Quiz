package com.example.quiz.adapter.out.repository.jpa;

import com.example.quiz.application.port.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
        QuestionDbo questionDbo = questionTransformer.toQuestionDbo(question);
        return questionTransformer.toQuestion(questionJpaRepository.save(questionDbo));
    }

    @Transactional
    @Override
    public List<com.example.quiz.domain.Question> findAll() {
        List<QuestionDbo> questions = questionJpaRepository.findAll();
        return questions.stream().map(questionTransformer::toQuestion).collect(Collectors.toList());
    }
}
