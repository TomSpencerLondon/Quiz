package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.adapter.in.web.QuizControllerTestFactory;
import com.example.quiz.application.QuizService;
import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.port.InMemoryQuizSessionRepository;
import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.application.port.QuizRepository;
import com.example.quiz.application.port.TokenGenerator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
class TestSessionConfiguration {
    @Primary
    @Bean
    QuizService createTestQuizService() {
        return new QuizService(new StubQuestionRepository());
    }

    @Primary
    @Bean
    QuizSessionService createTestQuizSessionService(QuizService quizService) {
        QuestionRepository questionRepository = QuizControllerTestFactory.createQuestionRepositoryWithSingleChoiceQuestion();
        QuizRepository quizRepository = QuizControllerTestFactory.createQuizRepositoryWithOneQuizWith(questionRepository.findAll().get(0));
        return new QuizSessionService(quizService, new InMemoryQuizSessionRepository(), quizRepository, new StubTokenGenerator());
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
