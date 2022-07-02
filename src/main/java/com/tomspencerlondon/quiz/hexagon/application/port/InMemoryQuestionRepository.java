package com.tomspencerlondon.quiz.hexagon.application.port;

import com.tomspencerlondon.quiz.hexagon.domain.ChoiceId;
import com.tomspencerlondon.quiz.hexagon.domain.Question;
import com.tomspencerlondon.quiz.hexagon.domain.QuestionId;

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

        question.choices().forEach(choice -> {
            if (choice.getId() == null) {
                choice.setId(ChoiceId.of(counter.getAndIncrement()));
            }
        });

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
