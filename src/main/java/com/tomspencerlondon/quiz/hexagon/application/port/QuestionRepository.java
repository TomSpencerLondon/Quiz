package com.tomspencerlondon.quiz.hexagon.application.port;

import com.tomspencerlondon.quiz.hexagon.domain.Question;
import com.tomspencerlondon.quiz.hexagon.domain.QuestionId;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    Question save(Question question);

    List<Question> findAll();

    Optional<Question> findById(QuestionId questionId);
}
