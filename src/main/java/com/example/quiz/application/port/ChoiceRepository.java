package com.example.quiz.application.port;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.ChoiceId;

import java.util.Optional;

public interface ChoiceRepository {
    Choice save(Choice choice);
    Optional<Choice> findById(ChoiceId questionId);
}

