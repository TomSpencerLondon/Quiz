package com.example.quiz.application.port;

import com.example.quiz.domain.Question;
import com.example.quiz.domain.QuestionId;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    Question save(Question question);

    List<Question> findAll();

    Optional<Question> findById(QuestionId questionId);
}
