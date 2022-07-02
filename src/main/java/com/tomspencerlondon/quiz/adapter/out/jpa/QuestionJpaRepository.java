package com.tomspencerlondon.quiz.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionJpaRepository extends JpaRepository<QuestionDbo, Long> {
    Optional<QuestionDbo> findByText(String text);
}
