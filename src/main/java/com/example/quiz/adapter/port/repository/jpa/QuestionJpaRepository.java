package com.example.quiz.adapter.port.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionJpaRepository extends JpaRepository<QuestionDto, Long>
{
  Optional<QuestionDto> findByText(String userName);
}
