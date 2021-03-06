package com.tomspencerlondon.quiz.adapter.in.web.answer;

import com.tomspencerlondon.quiz.hexagon.application.port.QuestionRepository;
import com.tomspencerlondon.quiz.hexagon.domain.Question;
import com.tomspencerlondon.quiz.hexagon.domain.QuestionId;
import com.tomspencerlondon.quiz.hexagon.domain.domain.QuestionBuilder;

import java.util.List;
import java.util.Optional;

public class StubQuestionRepository implements QuestionRepository {

    @Override
    public Question save(Question question) {
        return null;
    }

    @Override
    public List<Question> findAll() {
        Question question = new QuestionBuilder().withDefaultSingleChoice().build();
        return List.of(question);
    }

    @Override
    public Optional<Question> findById(QuestionId questionId) {
        Question question = new QuestionBuilder().withQuestionId(1L).withDefaultSingleChoice().build();
        return Optional.of(question);
    }
}
