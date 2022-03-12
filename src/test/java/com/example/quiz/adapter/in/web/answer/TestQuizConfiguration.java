package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.application.QuizService;
import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.SingleChoice;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.List;

@TestConfiguration
class TestQuizConfiguration {
    @Primary
    @Bean
    QuizService createTestQuizService() {
        final Question question = new Question(
                "Question 1",
                new SingleChoice(List.of(new Choice("Answer 1", true), new Choice("Answer 2"))));
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        inMemoryQuestionRepository.save(question);

        return new QuizService(inMemoryQuestionRepository);
    }
}
