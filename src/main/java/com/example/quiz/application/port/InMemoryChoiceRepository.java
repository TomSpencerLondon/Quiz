package com.example.quiz.application.port;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.ChoiceId;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryChoiceRepository implements ChoiceRepository {
    private final Map<ChoiceId, Choice> choices = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    @Override
    public Choice save(Choice choice) {
        if (choice.getId() == null) {
            choice.setId(ChoiceId.of(counter.getAndIncrement()));
        }

        choices.put(choice.getId(), choice);
        return choice;
    }

    @Override
    public Optional<Choice> findById(ChoiceId questionId) {
        return Optional.ofNullable(choices.get(questionId));
    }
}
