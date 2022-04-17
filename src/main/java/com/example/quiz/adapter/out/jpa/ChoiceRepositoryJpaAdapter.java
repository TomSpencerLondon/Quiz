package com.example.quiz.adapter.out.jpa;

import com.example.quiz.application.port.ChoiceRepository;
import com.example.quiz.domain.Choice;
import com.example.quiz.domain.ChoiceId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ChoiceRepositoryJpaAdapter implements ChoiceRepository {
    private final ChoiceJpaRepository choiceJpaRepository;
    private final ChoiceTransformer choiceTransformer;

    @Autowired
    public ChoiceRepositoryJpaAdapter(ChoiceJpaRepository choiceJpaRepository, ChoiceTransformer choiceTransformer) {
        this.choiceJpaRepository = choiceJpaRepository;
        this.choiceTransformer = choiceTransformer;
    }

    @Override
    public Choice save(Choice choice) {
        ChoiceDbo choiceDbo = choiceTransformer.toChoiceDbo(choice);
        return choiceTransformer.toChoice(choiceJpaRepository.save(choiceDbo));
    }

    @Override
    public Optional<Choice> findById(ChoiceId choiceId) {
        return choiceJpaRepository
                .findById(choiceId.id())
                .map(choiceTransformer::toChoice);
    }
}
