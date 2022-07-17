package com.tomspencerlondon.quiz.adapter.in.web.answer;

import com.tomspencerlondon.quiz.adapter.in.web.QuizControllerTestFactory;
import com.tomspencerlondon.quiz.hexagon.application.QuizService;
import com.tomspencerlondon.quiz.hexagon.application.QuizSessionService;
import com.tomspencerlondon.quiz.hexagon.application.port.*;
import com.tomspencerlondon.quiz.hexagon.domain.Question;
import com.tomspencerlondon.quiz.hexagon.domain.Quiz;
import com.tomspencerlondon.quiz.hexagon.domain.domain.QuestionBuilder;
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
        QuestionRepository questionRepository = QuestionRepositoryFactory.createQuestionRepositoryWithSingleChoiceQuestion();
        QuizRepository quizRepository = QuizControllerTestFactory.createQuizRepositoryWithOneQuizWith(questionRepository.findAll().get(0));
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
