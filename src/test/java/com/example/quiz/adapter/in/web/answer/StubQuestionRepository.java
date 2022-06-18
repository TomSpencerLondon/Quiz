package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.hexagon.application.port.QuestionRepository;
import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;

import com.example.quiz.hexagon.domain.Question;
import com.example.quiz.hexagon.domain.QuestionId;

import java.util.List;
import java.util.Optional;

public class StubQuestionRepository implements QuestionRepository {

    @Override
    public Question save(Question question) {
        return null;
    }

    @Override
    public List<Question> findAll() {
        return List.of(SingleChoiceQuestionTestFactory.createSingleChoiceQuestion());
    }

    @Override
    public Optional<Question> findById(QuestionId questionId) {
        Question question = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        question.setId(questionId);
        return Optional.of(question);
    }
}
