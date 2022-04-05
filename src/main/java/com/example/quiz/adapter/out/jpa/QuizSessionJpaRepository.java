package com.example.quiz.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizSessionJpaRepository extends JpaRepository<QuizSessionDbo, Long> {
}
