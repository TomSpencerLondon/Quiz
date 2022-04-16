package com.example.quiz.application.port;

import com.example.quiz.domain.Question;
import com.example.quiz.domain.QuestionId;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryQuestionRepository implements
        QuestionRepository {

    private final Map<QuestionId, Question> questions = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    @Override
    public Question save(Question question) {
        if (question.getId() == null) {
            question.setId(QuestionId.of(counter.getAndIncrement()));
        }
        questions.put(question.getId(), question);
        return question;
    }

    @Override
    public List<Question> findAll() {
        return List.copyOf(questions.values());
    }

    @Override
    public Optional<Question> findById(QuestionId questionId) {
        return Optional.ofNullable(questions.get(questionId));
    }
}
