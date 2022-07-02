package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.hexagon.application.QuizService;
import com.example.quiz.hexagon.application.QuizSessionService;
import com.example.quiz.hexagon.application.port.*;
import com.example.quiz.hexagon.domain.Question;
import com.example.quiz.hexagon.domain.Quiz;
import com.example.quiz.hexagon.domain.domain.QuestionBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.List;

@TestConfiguration
class TestSessionConfiguration {
    @Primary
    @Bean
    QuizService createTestQuizService() {
        return new QuizService(new StubQuestionRepository());
    }

    @Primary
    @Bean
    QuizSessionService createTestQuizSessionService() {
        Question question = new QuestionBuilder().withQuestionId(1L).withDefaultSingleChoice().build();
        Quiz quiz = new Quiz("Quiz 1", List.of(question.getId()));
        InMemoryQuizRepository quizRepository = new InMemoryQuizRepository();
        quizRepository.save(quiz);
        return new QuizSessionService(new InMemoryQuizSessionRepository(), quizRepository, new StubTokenGenerator());
    }

    @Primary
    @Bean
    QuestionRepository questionRepository() {
        return new StubQuestionRepository();
    }

    @Primary
    @Bean
    TokenGenerator createIdGenerator() {
        return new StubTokenGenerator();
    }
}
