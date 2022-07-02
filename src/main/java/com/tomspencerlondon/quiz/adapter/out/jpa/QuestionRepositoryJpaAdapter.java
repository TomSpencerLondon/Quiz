package com.tomspencerlondon.quiz.adapter.out.jpa;

import com.tomspencerlondon.quiz.hexagon.application.port.QuestionRepository;
import com.tomspencerlondon.quiz.hexagon.domain.Question;
import com.tomspencerlondon.quiz.hexagon.domain.QuestionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public Question save(Question question) {
        QuestionDbo questionDbo = questionTransformer.toQuestionDbo(question);
        return questionTransformer.toQuestion(questionJpaRepository.save(questionDbo));
    }

    @Override
    public List<Question> findAll() {
        List<QuestionDbo> questions = questionJpaRepository.findAll();
        return questions.stream()
                        .map(questionTransformer::toQuestion)
                        .toList();
    }

    @Override
    public Optional<Question> findById(QuestionId questionId) {
        return questionJpaRepository
                .findById(questionId.id())
                .map(questionTransformer::toQuestion);
    }
}
